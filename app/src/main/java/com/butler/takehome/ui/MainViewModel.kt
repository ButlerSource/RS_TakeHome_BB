package com.butler.takehome.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butler.takehome.data.WastePickupRepository
import com.butler.takehome.domain.model.Driver
import com.butler.takehome.domain.model.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WastePickupRepository) :
    ViewModel() {

    val driver = MutableLiveData<List<Driver>>()
    val routeForDriver = MutableLiveData<Route>()
    var currentDriverList: MutableList<Driver> = mutableListOf()
    var currentRoute: Route? = null

    fun loadDrivers() {
        if(currentDriverList.isNotEmpty()){
            emitCachedDrivers()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getDrivers().collect {
                    launch(Dispatchers.Main) {
                        driver.value = it
                        currentDriverList = it.toMutableList()
                        Log.d("MainViewModel", "driver list emitted")
                    }
                }
            }
        }
    }

    private fun emitCachedDrivers(){
        driver.value = currentDriverList
        Log.d("MainViewModel", "cached driver list emitted")
    }

    fun onDriverSelected(driver: Driver) {
        viewModelScope.launch {
            repository.getRoutesForDriver(driver.id).collect{route ->
                launch(Dispatchers.Main) {
                    currentRoute = route
                    routeForDriver.value = route
                }
            }
        }
    }

    fun onSortByLastName() {
        currentDriverList.sortBy {
            it.name.split(" ").last()
        }
        emitCachedDrivers()
    }
}