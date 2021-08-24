package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val details: String? = null
)