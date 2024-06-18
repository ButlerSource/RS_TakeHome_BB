package com.butler.takehome.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.butler.takehome.data.local.dao.DriverDao
import com.butler.takehome.data.local.dao.RouteDao
import com.butler.takehome.data.local.entity.Driver
import com.butler.takehome.data.local.entity.Route

@Database(
    entities = [
        Driver::class,
        Route::class
    ],
    version = 1
)
abstract class WastePickupDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun routeDao(): RouteDao
}