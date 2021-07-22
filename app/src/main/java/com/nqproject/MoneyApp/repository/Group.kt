package com.nqproject.MoneyApp.repository

import java.util.*


data class Group(
    val id: Int,
    val name: String,
    val totalCost: Double,
    val userBalance: Double,
    val icon: Int,
    val createDate: Date,
    var isFavourite: Boolean
)