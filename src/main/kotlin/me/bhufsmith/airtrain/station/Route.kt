package me.bhufsmith.airtrain.station

class Route (){


    private val rootStation = StationNode("root")
    private var currentStation: StationNode = rootStation
    private var lastStation:StationNode = rootStation
    private var reverse: Boolean = false
    private var numStations = 0

    /**
     * Add a station to the route
     * timeTo represents the time to get to the added station from the previous one.
     * timeFrom represents the time to go back to the previouse station from the added station.
     */
    fun addStation(name: kotlin.String, timeTo:Float, timeFrom:Float){
        val newStation =  if( numStations == 0) StationNode(name, rootStation)
                                            else StationNode(name, lastStation, timeFrom)
        lastStation.timeToNext = timeTo
        lastStation.nextStation = newStation

        lastStation = newStation
        numStations++
    }

    /**
     * Removes the named station if it exists, and all stations after it.
     * This will reset the route to the beginning
     */
    fun removeStation( name: kotlin.String){
        var station:StationNode = rootStation
        //This is to account for the root station
        var newNumStatios = 0

        while( station.nextStation != null ){

            if( station.nextStation!!.name == name ) {

                station.nextStation = null
                station.timeToNext = 0.0f

                this.lastStation = station
            }
            else{
                newNumStatios++
                station = station.nextStation!!
            }

        }
        this.numStations = newNumStatios
        this.currentStation = rootStation
    }

    /**
     * Get the next station on the route.
     */
    fun nextStation(): StationConnection {

        //Ensure we are headed in the correct direction.
        checkReverse()

        val nextStation: StationConnection
        when {
            //In this case we have an invalid route because we need at least two stations.
            lastStation == rootStation || numStations <=1 -> {
                throw InvalidRouteException("Route must have at least two stations. Current:$numStations")
            }

            //We need the previous station as the next one
            reverse -> {
                nextStation = StationConnection(currentStation.previousStation!!.name,
                        currentStation.timeToPrev)

                currentStation = currentStation.previousStation!!

                return nextStation
            }

            //In the normal case, we just want to gran the next station.
            else -> {
                nextStation = StationConnection(currentStation.nextStation!!.name,
                        currentStation.timeToNext)

                currentStation = currentStation.nextStation!!

                return  nextStation
            }
        }
    }

    /**
     * Set the direction of the train if needed.
     */
    fun checkReverse(){
        when{
            //We have reached the root and need to turn around
            reverse && currentStation.previousStation == rootStation -> {
                reverse = false
            }

            //We have reached the end of the line and need to turn around.
            !reverse && currentStation.nextStation == null -> {
                reverse = true
            }
        }
    }

    /**
     * reset the route back to the beginning.
     */
    fun reset(){
        this.currentStation = rootStation
    }

    /**
     * This class describes a station by name, and it's connecting stations.
     *
     * name: A unique name for this station
     * previousStation: the station before this one. If null, this is the first station
     * timeFrom: the time from this station to the parent station.
     * nextStation: the next station on the list. if null, this is the last station.
     * timeTo: the time to the next station
     */
    private data class StationNode(val name: kotlin.String = "",
                                   var previousStation:StationNode? = null,
                                   var timeToPrev: Float = 0.0f,
                                   var nextStation: StationNode? = null,
                                   var timeToNext: Float = 0.0f)

}