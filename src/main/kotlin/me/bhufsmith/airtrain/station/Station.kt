package me.bhufsmith.airtrain.station

/**
 * This class describes a station by name, and it's connecting stations.
 *
 * name: A unique name for this station
 * connections: A list of Connection that describe stations reachable from this station
 */
data class Station( val name:String,
                    val connections: List<Connection> )