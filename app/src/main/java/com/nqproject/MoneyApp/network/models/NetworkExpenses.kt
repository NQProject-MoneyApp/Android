package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NetworkAddExpenseRequest(
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
    val create_date: String,
    val participants: List<NetworkUser>,
)
