package com.butler.takehome.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Drivers(
    @SerialName("id") var id: String? = null,
    @SerialName("name") var name: String? = null
)
