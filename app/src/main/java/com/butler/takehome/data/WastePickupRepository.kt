package com.butler.takehome.data

import com.butler.takehome.domain.model.Driver
import com.butler.takehome.domain.model.Route
import kotlinx.coroutines.flow.Flow

interface WastePickupRepository {
    fun getDrivers(): Flow<List<Driver>>

    fun getRoutesForDriver(driverId: String): Flow<Route>
}