package com.butler.takehome.domain.model

import com.butler.takehome.data.local.entity.Route
import kotlinx.serialization.SerialName

data class Route (
    @SerialName("id") var id: Int,
    @SerialName("type") var type: String,
    @SerialName("name") var name: String
) {
    constructor(route: Route?) : this(route?.routeId ?: 0,
        route?.routeType ?: "Unknown",
        route?.routeName ?: "Unknown")
}