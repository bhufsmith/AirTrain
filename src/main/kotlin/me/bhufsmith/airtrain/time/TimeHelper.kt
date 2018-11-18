package me.bhufsmith.airtrain.time

import java.util.*

class TimeHelper( scale: Float,
                  private val simTimeMin:Float = (24 * 60).toFloat()  ) {


    val MILLI_SCALE:Float = scale
    val SECOND_SCALE:Float = MILLI_SCALE * 1000
    val MINUTE_SCALE:Float = SECOND_SCALE * 60

    val minPerHour = 60
    val secondsPerMin = 60
    val milliPerSecond = 1000

    private val startTime: Long = System.currentTimeMillis()

    /**
     * Helper function to wait for millis scaled to our sim time
     */
    fun waitFor(min: Float ){
        val millis = (min * MINUTE_SCALE).toLong()
        Thread.sleep( millis )
    }

    fun currentTime(): Float{
        val now = System.currentTimeMillis()
        val elapsed = now - startTime


        return ( elapsed / MINUTE_SCALE )
    }

    fun stopTime(): Boolean = currentTime() >= simTimeMin
}