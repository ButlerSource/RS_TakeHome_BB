package com.butler.takehome.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Driver(
    @PrimaryKey
    @ColumnInfo(name = "driver_id") val driverId: String,
    @ColumnInfo(name = "driver_name") val driverName: String,
)