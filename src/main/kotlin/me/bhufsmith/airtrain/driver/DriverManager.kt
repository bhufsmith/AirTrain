package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.station.StationConnection

interface DriverManager {

    /**
     * This is the function that should be called to register a new drive
     */
    fun registerDriver(name: String, location: String)

    /**
     * This function will return a list of available drivers at a given station
     */
    fun findDriversAtStation( station: String):List<Driver>

    /**
     * Returns a list of all available drivers
     */
    fun availableDrivers(): List<Driver>

    /**
     * Takes a driver back from the train and returns them to the pool.
     */
    fun returnDriver( driver: Driver )

    /**
     * Take a driver from the pool and use them to drive a train.
     */
    fun takeDriver( driver: Driver )

    /**
     * Take a driver from the pool and use them to drive a train.
     */
    fun takeDriverFromStation( station:String ):Driver

    /**
     * Can a driver keep going for the specified amount of time?
     */
    fun canDrive( driver: Driver, moreMin:Float):Boolean
}