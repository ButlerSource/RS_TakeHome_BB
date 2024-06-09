package com.butler.takehome.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: WastePickupDatabase? = null

        fun getDatabase(context: Context): WastePickupDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context,
                    WastePickupDatabase::class.java,
                    "wastepickup_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = db
                return db
            }
        }
    }
}