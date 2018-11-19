package me.bhufsmith.airtrain.train

import me.bhufsmith.airtrain.driver.Driver
import me.bhufsmith.airtrain.driver.DriverManager
import me.bhufsmith.airtrain.driver.InvalidDriverException
import me.bhufsmith.airtrain.messenger.Message
import me.bhufsmith.airtrain.messenger.MessageReceiver
import me.bhufsmith.airtrain.messenger.TrainMessageService
import me.bhufsmith.airtrain.station.InvalidRouteException
import me.bhufsmith.airtrain.station.Route
import me.bhufsmith.airtrain.station.StationConnection
import me.bhufsmith.airtrain.time.TimeHelper

/**
 * The train will start
 */
class AirTrain (val trainId:String,
                private val route: Route,
                private val driverManager: DriverManager,
                private val timeHelper: TimeHelper): Runnable, MessageReceiver {

    private val TIME_FOR_BOARD = 7.5f
    private val TIME_FOR_DRIVER = 5.5f
    private var currentDriver: Driver
    private var currentStation: String


    init {
        //Get the first station
        var nextStation = route.nextStation()
        this.currentStation = nextStation.station

        //Get our first driver
        val availableDriver = driverManager.takeDriverFromStation( currentStation )

        currentDriver = availableDriver
        currentDriver.messenger.subscribeForMessages( this )

        printEvent("Driver ${availableDriver.name} on board at ${this.currentStation}")
    }

    override fun messageArrived(receiverId: String, message: Message) {
        val urgentString = if(message.urgent) "- URGENT --" else ""
        println("\t -$urgentString [${message.simTime}: To-Driver: ${currentDriver.name}  From: ${message.senderName}]: ${message.message}")
    }

    /**
     * Check to see if the current driver has enough time to make it to the next station.
     * If not, we need to swap drivers before we go.
     */
    private fun checkDriverStatus( nextStation:StationConnection ){

        if( ! driverManager.canDrive( currentDriver, nextStation.timeTo ) ) {

            printEvent("Waiting for driver switch")
            timeHelper.waitFor(TIME_FOR_DRIVER)

            driverManager.returnDriver( currentDriver )
            currentDriver.messenger.unsubscribe( this )

            printEvent("Driver: ${currentDriver.name} has departed the train\n\n")
            currentDriver = driverManager.takeDriverFromStation( currentStation )
            currentDriver.messenger.subscribeForMessages( this )

            printEvent("Driver: ${currentDriver.name} on board at station $currentStation")
        }
    }

    fun printEvent( event: String ){
        println("[${timeHelper.currentTime()}: ${this.trainId}] - $event")
    }

    override fun run(){


        try {
            var nextStation = route.nextStation()
            while (!timeHelper.stopTime()) {

                //Wait for passengers
                printEvent("Waiting for passengers")
                timeHelper.waitFor(TIME_FOR_BOARD)

                //Make sure this driver can keep going.
                checkDriverStatus(nextStation)

                //Wait for travel to next stop
                currentDriver.messenger.canDisturb = false
                printEvent("Departing: $currentStation for ${nextStation.station} - ${nextStation.timeTo} min")
                timeHelper.waitFor(nextStation.timeTo)


                //Update station and allow driver tor receive messages.
                currentStation = nextStation.station
                printEvent("Arrived at ${currentStation}")
                currentDriver.messenger.canDisturb = true

                nextStation = route.nextStation()
            }

            driverManager.returnDriver( currentDriver )
        }
        catch ( route: InvalidRouteException ){
            printEvent("The route is invalid, and the train can not run. Need at least two stops on route.")
            System.exit( 1 )
        }
        catch ( driver: InvalidDriverException ){
            printEvent("There are no drivers at the station, The train can not continue. ")
            System.exit( 2 )
        }

    }
}