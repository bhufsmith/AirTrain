package me.bhufsmith.airtrain.station

/**
 * This class if for holding a station node, and the time to get to that station in minutes.
 *
 * destination: The station that is reachable from a given station
 * timeTo: The time in minutes to get from a given station to "destination"
 */
data class Connection (val destination:Station,
                       val timeTo: Int)