package me.bhufsmith.airtrain.train

import me.bhufsmith.airtrain.driver.Driver
import me.bhufsmith.airtrain.driver.DriverManager
import me.bhufsmith.airtrain.station.Connection
import me.bhufsmith.airtrain.station.Station


//Todo what about circular routes?

/**
 * The train will start
 */
class Train ( val trainId:String,
              private val route: List<Station>,
              private val driverManager: DriverManager ): Runnable {

    private val TIME_AT_STATION = 7.5
    private val TIME_FOR_DRIVER = 5.5
    private val currentDriver: Driver? = null
    //Move to the next stop (Wait time to next stop)

    //Driver switch

    private fun hasValidRoute():Boolean
    {
        //It is not much of a route if we have nowhere to go
        //We need at least two stations to travel between.
        if( route.size < 2 ) return false

        //We need to check every station to make sure it can reach the next station.
        var nextStop:Station
        for( index in 0 until ( route.size - 1 ) ){

            nextStop = route[ index + 1]

            //If this station does not connect to the next one return false
            if( route[index].connections.find { it.destination == nextStop } == null ){
                return false
            }
        }
        return true
    }


    override fun run(){

        if ( hasValidRoute() ) {
            println("Train: $trainId is preparing to leave from station: ${route.first()}")
            val availableDrivers = driverManager.findDriversAtStation( route.first() )

            if( availableDrivers.any() ){
                val firstDriver = availableDrivers.first()
                driverManager.takeDriver( firstDriver )
                println("Train: $trainId has taken driver: ${firstDriver.name}" )
            }
        }
    }
}