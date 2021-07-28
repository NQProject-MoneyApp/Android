package com.nqproject.MoneyApp.network.models

import java.util.*

data class NetworkAddExpenseRequest(
    val name: String,
    val amount: Float,
)

data class NetworkExpensesResponse (
    val name: String,
    val author: NetworkUser,
    val amount: Double,
    val create_date: Date
)
