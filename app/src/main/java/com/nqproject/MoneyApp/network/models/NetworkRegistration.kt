package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRegistrationRequest(
    val username: String,
    val password1: String,
    val password2: String,
    val email: String,
)

@Serializable
data class NetworkRegistrationResponse(
    val key: String? = null,
    //TODO: change on backend!
    @SerialName("non_field_errors")
    val error: String? = null,
)
