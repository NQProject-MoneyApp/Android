package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkGroupUsersResponse (
    val user: NetworkUser,
    val balance: Double
)