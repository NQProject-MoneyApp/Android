package com.nqproject.MoneyApp.repository

import android.os.Parcelable
import com.nqproject.MoneyApp.network.models.NetworkUser
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import java.util.*


@Parcelize
data class SuggestedPayment(
    val paidBy: User,
    val paidTo: User,
    val amount: Double,
) : Parcelable