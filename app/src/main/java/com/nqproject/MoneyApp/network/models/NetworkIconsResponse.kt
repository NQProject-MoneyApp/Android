package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkIconsResponse (
    val icons: List<Int>
)