package com.butler.takehome.data.remote.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val RS_API_BASE_URL = "https://d49c3a78-a4f2-437d-bf72-569334dea17c.mock.pstmn.io/"
class WastePickupAPI {

    companion object{
        fun create(): WastePickupService{
            //For logging retrofit events
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val networkJson = Json{ ignoreUnknownKeys = true}


            val retrofit = Retrofit.Builder()
                .baseUrl(RS_API_BASE_URL)
                .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
                .client(httpClient.build())
                .build()

            return retrofit.create(WastePickupService::class.java)
        }
    }
}