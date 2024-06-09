package com.butler.takehome.data.remote.network

import com.butler.takehome.data.remote.dtos.WastePickupResponse
import retrofit2.Call
import retrofit2.http.GET

interface WastePickupService {
    @GET("data")
    fun getWastePickupData(): Call<WastePickupResponse>
}