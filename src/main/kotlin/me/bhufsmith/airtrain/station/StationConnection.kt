package me.bhufsmith.airtrain.station

import kotlin.String

/**
 * This class describes a station by name, and it's connecting stations.
 *
 * name: A unique name for this station
 * previousStation: the station before this one. If null, this is the first station
 * timeFrom: the time from this station to the parent station.
 * nextStation: the next station on the list. if null, this is the last station.
 * timeTo: the time to the next station
 */
data class StationConnection(val station: String = "",
                             val timeTo:Float = 0.0f)