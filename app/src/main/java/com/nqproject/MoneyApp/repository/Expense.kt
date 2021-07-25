package com.nqproject.MoneyApp.repository

import java.util.*

data class Expense (
    val name: String,
    val amount: Double,
    val createDate: Date,
    val author: User
)