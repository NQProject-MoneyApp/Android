package com.nqproject.MoneyApp.repository

import android.os.Parcelable
import com.nqproject.MoneyApp.network.models.ExpenseType
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Expense(
    val pk: Int,
    val groupId: Int,
    val name: String,
    val amount: Double,
    val createDate: Date,
    val paidBy: String,
    val paymentTo: String?,
    val type: ExpenseType,
    val author: User,
    val participants: List<User>,
) : Parcelable
