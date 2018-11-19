package me.bhufsmith.airtrain

import me.bhufsmith.airtrain.driver.DriverManager
import me.bhufsmith.airtrain.driver.SimpleDriverManager
import me.bhufsmith.airtrain.messenger.TrainMessageService
import me.bhufsmith.airtrain.messenger.TrainDriverMessageService
import me.bhufsmith.airtrain.station.Route
import me.bhufsmith.airtrain.time.TimeHelper
import me.bhufsmith.airtrain.train.AirTrain
import me.bhufsmith.airtrain.train.AnoyingPassengers
import me.bhufsmith.airtrain.train.RandomNess
import java.util.*

class AirTrainSim

fun main( args: Array<String> ){

    //Instantiate the services we need for dependency injection.
    val timeHelper = TimeHelper( 0.001f)
    val driverRegistrationKey = UUID.randomUUID().toString()
    val trainMessageService:TrainMessageService = TrainDriverMessageService(driverRegistrationKey, timeHelper)
    val driverManager = SimpleDriverManager(timeHelper, trainMessageService, driverRegistrationKey)

    //Add Terminals to the route.
    val route = Route()
    route.addStation("A", 0.0f, 0.0f)
    route.addStation("B", 9.0f, 11.0f)


    //UNCOMMENT THE FOLLOWING LINE TO ADD ANOTHER STATION
    //route.addStation("C", 22.5f, 17.5f)

    //Add drivers to each station so that we always have a driver to pick up.
    populateDrivers( route, driverManager )

    //These are our two threads that will run the train, and the passenger messages
    val airTrain = AirTrain("EWR AirTrain", route, driverManager, timeHelper)
    val anoyingPassengers = AnoyingPassengers( trainMessageService, timeHelper, 5, 2)

    Thread(airTrain).start()
    Thread(anoyingPassengers).start()
}

private fun populateDrivers(route: Route, driverManager: DriverManager){

    route.stationList().forEach{stationName ->
        driverManager.registerDriver( RandomNess.randomName(), stationName)
        driverManager.registerDriver( RandomNess.randomName(), stationName)
        driverManager.registerDriver( RandomNess.randomName(), stationName)
        driverManager.registerDriver( RandomNess.randomName(), stationName)
    }

}