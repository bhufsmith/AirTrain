package me.bhufsmith.airtrain.driver

import me.bhufsmith.airtrain.station.Station

class SimpleDriverManager: DriverManager {

    private val drivers = mutableMapOf<Int, DriverDetail>()

    override fun registerDriver(name: String, location: Station) {
        val driver = Driver( name )
        val driverDetail = DriverDetail( driver, location, DriverStatus.WAITING )

        drivers[ driver.driverId ] = driverDetail
    }

    override fun findDriversAtStation(station: Station): List<Driver> {
      return this.drivers.values.filter { it.location == station }
                                .map { it.driver }
    }

    override fun availableDrivers(): List<Driver>{

      return this.drivers.values.filter{ it.driverStatus == DriverStatus.WAITING }
                                .map{ it.driver }
                                .filter { it.canDrive() }

    }

    override fun returnDriver(driver: Driver) {

    }

    override fun takeDriver(driver: Driver) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class DriverDetail(val driver: Driver, var location: Station, var driverStatus: DriverStatus)

}