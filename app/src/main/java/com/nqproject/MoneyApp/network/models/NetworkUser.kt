package com.nqproject.MoneyApp.network.models

data class NetworkUser (
    val pk: Int,
    val username: String,
    val email: String,
    val balance: Double? // TODO: To delete? - Is it ever returned by the endpoint?
)