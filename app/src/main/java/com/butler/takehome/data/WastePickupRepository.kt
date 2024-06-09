package com.butler.takehome.data

import com.butler.takehome.data.model.Driver
import com.butler.takehome.data.model.Route
import kotlinx.coroutines.flow.Flow

interface WastePickupRepository {
    suspend fun getDrivers(): Flow<Driver>

    suspend fun getRoutesForDriver(driverId: String): Flow<Route>
}