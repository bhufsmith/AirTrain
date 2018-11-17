package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.station.Station

data class Driver (val name: String = "",
                   val location: Station? = null )


{
    companion object {
        private var DRIVER_ID = 0
    }

    val driverId = Driver.DRIVER_ID++

    private val MAX_DRIVE_MIN = (8 * 60)
    private var driveTime = 0

    fun canDrive():Boolean {
        return true
    }

    fun appendDriveTime( min: Int ) { this.driveTime += min }

}

