package com.nqproject.MoneyApp.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Group(
    val id: Int,
    val name: String,
    val totalCost: Double,
    val userBalance: Double,
    val icon: Int,
    val createDate: Date,
    var isFavourite: Boolean
) : Parcelable