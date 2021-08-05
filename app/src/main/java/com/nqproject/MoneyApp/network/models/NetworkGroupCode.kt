package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkGroupCodeResponse (
    val code: String,
)

@Serializable
data class NetworkGroupCodeRequest(
    val group: Int
)