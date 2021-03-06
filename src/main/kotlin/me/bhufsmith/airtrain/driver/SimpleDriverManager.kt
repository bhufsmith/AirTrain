package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.messenger.TrainMessageService
import me.bhufsmith.airtrain.time.TimeHelper

class SimpleDriverManager(private val timeHelper: TimeHelper,
                          private val messageService: TrainMessageService,
                          private val driverKey:String): DriverManager {

    private val MAX_DRIVE_MIN = (8 * 60).toFloat()
    private val drivers = mutableMapOf<Int, DriverDetail>()
    private var driverId = 0

    override fun registerDriver(name: String, location: String) {
        val driverMessenger = messageService.registerDriver(name, driverKey)
        val driver = Driver( driverId++, name, driverMessenger )

        val driverDetail = DriverDetail( driver, location, DriverStatus.WAITING, -1.0f)

        synchronized(this.drivers) {
            drivers[driver.driverId] = driverDetail
        }
    }

    override fun findDriversAtStation(station: String): List<Driver> {

        synchronized( this.drivers ) {
            return this.drivers.values.filter { it.location == station }
                                        .map { it.driver }
        }
    }

    override fun availableDrivers(): List<Driver>{
        synchronized( this.drivers ) {

            return this.drivers.values.filter { it.driverStatus == DriverStatus.WAITING }
                                                .map { it.driver }
        }

    }

    override fun returnDriver(driver: Driver) {

        synchronized( this.drivers ) {
            val driverDetail = this.drivers[driver.driverId]

            if (driverDetail != null) {
                driverDetail.driverStatus = DriverStatus.OFF
                println("[Drive-Time: ${timeHelper.currentTime() - driverDetail.shiftStart}]" )
            } else {
                throw InvalidDriverException("Can not return invalid driver to station.")
            }
        }
    }

    override fun takeDriver(driver: Driver) {

        synchronized( this.drivers ) {

            val driverDetail = this.drivers[driver.driverId]

            if (driverDetail != null ) {
                driverDetail.driverStatus = DriverStatus.DRIVING
                driverDetail.shiftStart = timeHelper.currentTime()
                messageService.setCurrentDriver( driverDetail.driver.messenger )

            } else {
                throw InvalidDriverException("Invalid driver, or driver is off.")
            }
        }
    }

    override fun takeDriverFromStation(station: String):Driver {

        synchronized( this.drivers ) {
            //Find a driver who is waiting at the target station.
            val driverDetail = this.drivers.values
                                            .find { driverDetail ->
                                                driverDetail.location == station
                                                 && driverDetail.driverStatus == DriverStatus.WAITING
                                            }

            if (driverDetail != null ) {
                driverDetail.shiftStart = timeHelper.currentTime()
                driverDetail.driverStatus = DriverStatus.DRIVING
                messageService.setCurrentDriver( driverDetail.driver.messenger )
            } else {
                throw InvalidDriverException("No Driver to pick up.")
            }

            return driverDetail.driver
        }
    }

    override fun canDrive( driver: Driver, moreMin: Float  ):Boolean {

        synchronized( this.drivers ){
            val driverDetail = this.drivers[ driver.driverId ]

            if(driverDetail !=null ){
                return (timeHelper.currentTime() - driverDetail.shiftStart + moreMin ) <= MAX_DRIVE_MIN
            }
        }
        return false
    }

    private data class DriverDetail(val driver: Driver,
                                    var location: String,
                                    var driverStatus: DriverStatus,
                                    var shiftStart: Float)

}