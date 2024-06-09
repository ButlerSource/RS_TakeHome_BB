package com.butler.takehome.di

import android.content.Context
import com.butler.takehome.data.WastePickupRepository
import com.butler.takehome.data.WastePickupRepositoryImpl
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
    fun provideWastePickupRepository(@ApplicationContext context: Context): WastePickupRepository {
        return WastePickupRepositoryImpl(context)
    }
}