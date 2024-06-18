package com.butler.takehome.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.butler.takehome.data.local.entity.Driver
import kotlinx.coroutines.flow.Flow

@Dao
interface DriverDao {
    @Query("SELECT * FROM Driver")
    fun getAll(): Flow<List<Driver>>

    @Query("SELECT * FROM Driver WHERE driver_id = :driverId LIMIT 1")
    fun getById(driverId: String): Driver

    @Query("SELECT * FROM Driver WHERE driver_name LIKE :name LIMIT 1")
    fun findByName(name: String): Driver

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(driver: List<Driver>)

    @Delete
    fun delete(driver: Driver)

    @Query("DELETE FROM Driver")
    fun deleteAll()
}