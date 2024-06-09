package com.butler.takehome.data

import android.content.Context
import android.util.Log
import com.butler.takehome.data.local.WastePickupDatabase
import com.butler.takehome.data.model.Driver
import com.butler.takehome.data.model.Route
import com.butler.takehome.data.remote.dtos.Drivers
import com.butler.takehome.data.remote.dtos.Routes
import com.butler.takehome.data.remote.network.WastePickupAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class WastePickupRepositoryImpl(val context: Context) : WastePickupRepository {
    //TODO: Handle Network/Database Errors
    override suspend fun getDrivers(): Flow<Driver> {
        val driverDao = WastePickupDatabase.getDatabase(context).driverDao()

        //1: Fetch the latest data from the web service
        val service = WastePickupAPI.create()
        val response = service.getWastePickupData().execute()


        //2: Save to the local database
        if (response.isSuccessful && response.body() != null) {
            saveDriversToDB(response.body()!!.drivers)
            saveRoutesToDB(response.body()!!.routes)
        }

        //3: Return all Drivers from the database
        return driverDao.getAll().map {
            Driver(it)
        }.asFlow()
    }

    override suspend fun getRoutesForDriver(driverId: String): Flow<Route> {
        val routeDao = WastePickupDatabase.getDatabase(context).routeDao()

        val driverIdInt = try {
            driverId.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
        Log.d("Repository", "driverID: $driverIdInt")


        //1: return a route if it is associated with a driver ID
        val routePrimary = routeDao.getByDriverId(driverIdInt)
        if (routePrimary != null) return flow { emit(Route(routePrimary)) }

        //2: return a backup route
        val routes = routeDao.getAll()
        routes.forEach {
            Log.d("Repository", "Route: ${it.routeName} , ${it.routeType}")
        }
        var routeBackup = if (driverIdInt % 2 == 0) {
            routes.first { it.routeType == "R" }
        } else if (driverIdInt % 5 == 0) {
            routes.filter { it.routeType == "C" }.elementAt(1)
        } else {
            null
        }

        if (routeBackup == null) {
            routeBackup = routes.last { it.routeType == "I" }
        }

        return flow { emit(Route(routeBackup)) }
    }

    private suspend fun saveDriversToDB(drivers: List<Drivers>) {
        val driverDao = WastePickupDatabase.getDatabase(context).driverDao()
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

    private suspend fun saveRoutesToDB(routes: List<Routes>) {
        val routeDao = WastePickupDatabase.getDatabase(context).routeDao()
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