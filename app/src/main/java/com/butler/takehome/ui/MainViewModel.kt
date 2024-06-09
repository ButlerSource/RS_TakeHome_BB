package com.butler.takehome.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butler.takehome.data.WastePickupRepository
import com.butler.takehome.data.model.Driver
import com.butler.takehome.data.model.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WastePickupRepository) :
    ViewModel() {

    val driver = MutableLiveData<Driver>()
    val routeForDriver = MutableLiveData<Route>()
    var currentDriverList: List<Driver> = emptyList()

    fun loadDrivers() {
        if(currentDriverList.isNotEmpty()){
            currentDriverList.forEach {
                driver.value = it
                Log.d("MainViewModel", "cached driver emitted: ${it.name}")
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getDrivers().collect {
                    launch(Dispatchers.Main) {
                        driver.value = it
                        Log.d("MainViewModel", "driver emitted: ${it.name}")
                    }
                }
            }
        }
    }

    fun onDriverSelected(driver: Driver) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRoutesForDriver(driver.id).collect{route ->
                launch(Dispatchers.Main) {
                    routeForDriver.value = route
                }
            }
        }
    }
}