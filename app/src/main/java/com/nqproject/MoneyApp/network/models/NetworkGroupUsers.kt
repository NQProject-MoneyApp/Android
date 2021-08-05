package com.nqproject.MoneyApp.network.models

data class NetworkGroupUsersResponse (
    val user: NetworkUser,
    val balance: Double?
)