package com.nqproject.MoneyApp.network.models


data class NetworkGroupCodeResponse (
    val code: String,
)

data class NetworkGroupCodeRequest(
    val group_id: Int
)