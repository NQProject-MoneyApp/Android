package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NetworkExpensesRequest(
    val name: String,
    val amount: Float,
    val participants: List<Int>,
)

@Serializable
data class NetworkExpensesResponse(
    val pk: Int,
    val group_id: Int,
    val name: String,
    val author: NetworkUser,
    val amount: Double,
    @SerialName("create_date")
    val createDate: String,
    val participants: List<NetworkUser>,
)
