package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.*

@Serializable
data class NetworkLoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class NetworkLoginResponse(
    val key: String? = null,
    //TODO: change on backend!
    @SerialName("non_field_errors")
    val error: String? = null
)

