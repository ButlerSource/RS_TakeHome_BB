package com.butler.takehome.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.butler.takehome.data.local.entity.Route

@Dao
interface RouteDao {
    @Query("SELECT * FROM Route")
    fun getAll(): List<Route>

    @Query("SELECT * FROM Route WHERE route_id = :driverId LIMIT 1")
    fun getByDriverId(driverId: Int): Route?

    @Query("SELECT * FROM Route WHERE route_id = :routeId LIMIT 1")
    fun findById(routeId: Int): Route

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(routes: List<Route>)

    @Delete
    fun delete(route: Route)

}