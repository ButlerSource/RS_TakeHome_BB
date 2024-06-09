package com.butler.takehome.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WastePickupResponse(
    @SerialName("drivers") val drivers: List<Drivers> = listOf(),
    @SerialName("routes") val routes: List<Routes> = listOf()
)
