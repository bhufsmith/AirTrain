package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.station.Station

interface DriverManager {

    /**
     * This is the function that should be called to register a new drive
     */
    fun registerDriver(name: String, location: Station)

    /**
     * This function will return a list of available drivers at a given station
     */
    fun findDriversAtStation( station:Station ):List<Driver>

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
}