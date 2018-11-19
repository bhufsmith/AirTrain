package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.messenger.SimpleDriverMessengerUser

data class Driver (val driverId:Int,
                   val name: String = "",
                   val messenger: SimpleDriverMessengerUser)
