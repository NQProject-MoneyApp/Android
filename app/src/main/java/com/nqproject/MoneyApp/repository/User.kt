package com.nqproject.MoneyApp.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name: String,
    val email: String,
    val balance: Double
) : Parcelable