package com.nqproject.MoneyApp.repository


data class Group(
    val id: Int,
    val name: String,
    val amount: Double,
    val icon: Int,
    var isFavourite: Boolean
)