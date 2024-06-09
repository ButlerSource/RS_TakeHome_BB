package com.butler.takehome.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Route(
    @PrimaryKey
    @ColumnInfo(name = "route_id") val routeId: Int,
    @ColumnInfo(name = "route_name") val routeName: String,
    @ColumnInfo(name = "route_type") val routeType: String,
)