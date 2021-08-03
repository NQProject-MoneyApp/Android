package com.nqproject.MoneyApp.network.models

import java.util.*

data class NetworkAddExpenseRequest(
    val name: String,
    val amount: Float,
)

data class NetworkExpensesResponse(
    val pk: Int,
    val group_id: Int,
    val name: String,
    val author: NetworkUser,
    val amount: Double,
    val create_date: Date
)


data class NetworkExpenseDetailsResponse(
    val pk: Int,
    val group_id: Int,
    val name: String,
    val author: NetworkUser,
    val amount: Double,
    val create_date: Date
)