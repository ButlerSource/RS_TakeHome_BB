package com.butler.takehome.data

import android.content.Context
import android.util.Log
import com.butler.takehome.data.local.WastePickupDatabase
import com.butler.takehome.domain.model.Driver
import com.butler.takehome.domain.model.Route
import com.butler.takehome.data.remote.dtos.Drivers
import com.butler.takehome.data.remote.dtos.Routes
import com.butler.takehome.data.remote.network.WastePickupAPI
import com.butler.takehome.data.remote.network.WastePickupService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex

class WastePickupRepositoryImpl
    (val db: WastePickupDatabase,
     val service: WastePickupService,
     val context: Context) : WastePickupRepository {
    //TODO: Handle Network/Database Errors. Listen for network with ConnectivityManager
    override fun getDrivers(): Flow<List<Driver>> {
        val driverDao = db.driverDao()

        //1: Fetch the latest data from the web service
        val response = service.getWastePickupData().execute()

        //2: Save to the local database
        if (response.isSuccessful && response.body() != null) {
            saveDriversToDB(response.body()!!.drivers)
            saveRoutesToDB(response.body()!!.routes)
        }

        //3: Return all Drivers from the database
        return driverDao.getAll().map {
            it.map { driver ->
                Driver(driver)
            }
        }
    }

    override fun getRoutesForDriver(driverId: String): Flow<Route> {
        val routeDao = db.routeDao()

        val driverIdInt = try {
            driverId.toInt()
        } catch (e: NumberFormatException) {
            -1
        }

        //1: return a route if it is associated with a driver ID
        val routePrimary = routeDao.getByDriverId(driverIdInt).map { Route(it) }


        val routes = routeDao.getAll().map { Route(it) }

        return routePrimary.onEmpty {
            if (driverIdInt % 2 == 0) {
                routes.filter { it.type == "R" }.take(1)
            } else if (driverIdInt % 5 == 0) {
                routes.filter { it.type == "C" }.drop(1).take(1)
            } else {
                routes.filter { it.type == "I" }.take(1)
            }
        }
    }

    private fun saveDriversToDB(drivers: List<Drivers>) {
        val driverDao = db.driverDao()
        val driverEntities = drivers.map {
            com.butler.takehome.data.local.entity.Driver(
                it.id ?: "Unknown",
                it.name ?: "Unknown"
            )
        }

        if (driverEntities.isNotEmpty()) {
            driverDao.insertAll(driverEntities)
        }
    }

    private fun saveRoutesToDB(routes: List<Routes>) {
        val routeDao = db.routeDao()
        val routeEntities = routes.map {
            com.butler.takehome.data.local.entity.Route(
                it.id ?: -2,
                it.name ?: "Unknown",
                it.type ?: "Unknown"
            )
        }

        if (routeEntities.isNotEmpty()) {
            routeDao.insertAll(routeEntities)
        }
    }
}