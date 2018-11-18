package me.bhufsmith.airtrain

import me.bhufsmith.airtrain.driver.SimpleDriverManager
import me.bhufsmith.airtrain.messenger.TrainMessageService
import me.bhufsmith.airtrain.messenger.TrainDriverMessageService
import me.bhufsmith.airtrain.station.Route
import me.bhufsmith.airtrain.time.TimeHelper
import me.bhufsmith.airtrain.train.AirTrain
import me.bhufsmith.airtrain.train.AnoyingPassengers
import java.util.*

class AirTrain

fun main( args: Array<String> ){

    val timeHelper = TimeHelper( 0.001f)
    val driverManager = SimpleDriverManager(timeHelper)
    val driverRegistrationKey = UUID.randomUUID().toString()
    val trainMessageService:TrainMessageService = TrainDriverMessageService(driverRegistrationKey)

    val route = Route()
    route.addStation("A", 0.0f, 0.0f)
    route.addStation("B", 10.0f, 15.0f)
    route.addStation("C", 100.0f, 130.0f)

    driverManager.registerDriver("Alan", "A")
    driverManager.registerDriver("Meagan", "A")
    driverManager.registerDriver("Tommy", "B")
    driverManager.registerDriver("Lindsy", "B")



    val airTrain = AirTrain("The BFT", route, driverManager, timeHelper,
                                trainMessageService, driverRegistrationKey)

    val anoyingPassengers = AnoyingPassengers( trainMessageService, timeHelper, 5, 2)
    Thread(airTrain).start()
    Thread(anoyingPassengers).start()

}
