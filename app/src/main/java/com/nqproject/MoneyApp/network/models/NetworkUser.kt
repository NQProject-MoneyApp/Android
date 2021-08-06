package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser (
    val pk: Int,
    val username: String,
    val email: String,
)