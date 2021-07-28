package com.nqproject.MoneyApp.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Expense (
    val name: String,
    val amount: Double,
    val createDate: Date,
    val author: User
) : Parcelable