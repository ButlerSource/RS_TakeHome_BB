package com.butler.takehome.di

import android.content.Context
import androidx.room.Room
import com.butler.takehome.data.WastePickupRepository
import com.butler.takehome.data.WastePickupRepositoryImpl
import com.butler.takehome.data.local.WastePickupDatabase
import com.butler.takehome.data.remote.network.WastePickupAPI
import com.butler.takehome.data.remote.network.WastePickupService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideWastePickupService(): WastePickupService {
        return WastePickupAPI.create()
    }
}