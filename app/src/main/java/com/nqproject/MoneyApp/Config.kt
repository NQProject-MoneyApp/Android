package com.nqproject.MoneyApp

import android.os.Build
import java.time.Duration

object Config {
    val MAIN_TAG = "MONEY_APP"

    val SOCKET_TIMEOUT: Duration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Duration.ofSeconds(30)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}