package com.butler.takehome.domain.model

import com.butler.takehome.data.local.entity.Driver
import kotlinx.serialization.SerialName

data class Driver (
    @SerialName("id") var id: String,
    @SerialName("name") var name: String
) {
    constructor(driverEntity: Driver) : this(id = driverEntity.driverId, name = driverEntity.driverName)
}