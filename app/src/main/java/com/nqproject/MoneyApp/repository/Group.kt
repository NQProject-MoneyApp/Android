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
    val icon: MoneyAppIcon,
    val createDate: Date,
    val isFavourite: Boolean,
    val members: List<User>,
) : Parcelable