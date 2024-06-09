package com.butler.takehome

import android.app.Application
import com.butler.takehome.data.WastePickupRepository
import com.butler.takehome.data.WastePickupRepositoryImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WastePickupApplication: Application()