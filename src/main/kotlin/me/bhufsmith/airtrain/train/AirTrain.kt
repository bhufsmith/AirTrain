package me.bhufsmith.airtrain.train

import me.bhufsmith.airtrain.driver.Driver
import me.bhufsmith.airtrain.driver.DriverManager
import me.bhufsmith.airtrain.messenger.TrainMessageService
import me.bhufsmith.airtrain.station.Route
import me.bhufsmith.airtrain.station.StationConnection
import me.bhufsmith.airtrain.time.TimeHelper

/**
 * The train will start
 */
class AirTrain (val trainId:String,
                private val route: Route,
                private val driverManager: DriverManager,
                private val timeHelper: TimeHelper,
                private val trainMessageService: TrainMessageService,
                private val driverRegistationKey: String): Runnable {

    private val TIME_FOR_BOARD = 7.5f
    private val TIME_FOR_DRIVER = 5.5f
    private var currentDriver: Driver? = null
    private var currentStation: String = ""


    private fun findFirstDriverAndStation(){

        //Get the first station
        var nextStation = route.nextStation()
        this.currentStation = nextStation.station

        //Get our first driver
        val availableDriver = driverManager.takeDriverFromStation( currentStation )

        currentDriver = availableDriver
        trainMessageService.registerDriver( currentDriver!!.name, driverRegistationKey)

        printEvent("Driver ${availableDriver.name} on board at ${this.currentStation}")
    }

    /**
     * Check to see if the current driver has enough time to make it to the next station.
     * If not, we need to swap drivers before we go.
     */
    private fun checkDriverStatus( nextStation:StationConnection ){

        if( ! driverManager.canDrive( currentDriver!!, nextStation.timeTo ) ) {

            printEvent("Waiting for driver switch")
            timeHelper.waitFor(TIME_FOR_DRIVER)

            driverManager.returnDriver( currentDriver!! )
            printEvent("Driver: ${currentDriver!!.name} has departed the train\n\n")
            currentDriver = driverManager.takeDriverFromStation( currentStation )

            //Register the new driver to the message service
            trainMessageService.registerDriver( currentDriver!!.name, driverRegistationKey)
            printEvent("Driver: ${currentDriver!!.name} on board at station $currentStation")
        }
    }

    fun printEvent( event: String ){
        println("[${this.trainId}] - $event")
    }

    override fun run(){

        findFirstDriverAndStation()

        var nextStation = route.nextStation()
        while( ! timeHelper.stopTime()  ){

            //Wait for passengers
            printEvent("Waiting for passengers")
            timeHelper.waitFor( TIME_FOR_BOARD )

            //Make sure this driver can keep going.
            checkDriverStatus( nextStation )

            //Wait for travel to next stop
            printEvent("Departing: $currentStation for ${nextStation.station} - ${nextStation.timeTo} min")
            timeHelper.waitFor( nextStation.timeTo )

            currentStation = nextStation.station
            printEvent("Arrived at ${currentStation}")

            nextStation = route.nextStation()
        }
    }
}