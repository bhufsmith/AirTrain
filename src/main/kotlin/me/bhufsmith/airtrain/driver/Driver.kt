package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.station.Station

data class Driver (val name: String = "",
                   val location: Station? = null,
                   val status: DriverStatus = DriverStatus.WAITING )