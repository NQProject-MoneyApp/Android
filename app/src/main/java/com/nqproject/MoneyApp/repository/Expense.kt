package com.nqproject.MoneyApp.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Expense(
    val pk: Int,
    val groupId: Int,
    val name: String,
    val amount: Double,
    val createDate: Date,
    val author: User,
    val participants: List<User>,
) : Parcelable
