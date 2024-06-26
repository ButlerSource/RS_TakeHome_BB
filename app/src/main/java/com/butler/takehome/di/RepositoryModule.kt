package com.butler.takehome.di

import android.content.Context
import com.butler.takehome.data.WastePickupRepository
import com.butler.takehome.data.WastePickupRepositoryImpl
import com.butler.takehome.data.local.WastePickupDatabase
import com.butler.takehome.data.remote.network.WastePickupService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideWastePickupRepository(db: WastePickupDatabase,service: WastePickupService, @ApplicationContext context: Context): WastePickupRepository {
        return WastePickupRepositoryImpl(db, service, context)
    }
}