package com.nqproject.MoneyApp.network.models

import com.google.gson.annotations.SerializedName

data class NetworkRegistrationRequest(
    val username: String,
    val password1: String,
    val password2: String,
    val email: String,
)

data class NetworkRegistrationResponse(
    val key: String?,
    //TODO: change on backend!
    @SerializedName("non_field_errors")
    val error: String?,
)
