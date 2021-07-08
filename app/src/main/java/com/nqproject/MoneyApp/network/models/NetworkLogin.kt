package com.nqproject.MoneyApp.network.models

import com.google.gson.annotations.SerializedName

data class NetworkLoginRequest(
    val username: String,
    val password: String,
)

data class NetworkLoginResponse(
    val key: String?,
    //TODO: change on backend!
    @SerializedName("non_field_errors")
    val error: String?,
)

