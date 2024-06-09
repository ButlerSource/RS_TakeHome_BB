package com.butler.takehome.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Routes(
    @SerialName("id") var id: Int? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("name") var name: String? = null
)