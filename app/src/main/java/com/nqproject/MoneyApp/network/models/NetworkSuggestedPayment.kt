package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkSuggestedPayment(
    @SerialName("paid_by")
    val paidBy: NetworkUser,
    @SerialName("paid_to")
    val paidTo: NetworkUser,
    val amount: Double,
)