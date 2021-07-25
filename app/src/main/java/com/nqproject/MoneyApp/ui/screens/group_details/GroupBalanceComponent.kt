package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import java.util.*

@Composable
fun GroupBalanceComponent(userBalance: UserBalance) {

    Row {
        Text(
            modifier = Modifier.weight(1f),
            text = userBalance.name,
            color = Color.White
        )
        Text(String.format(Locale.US, "%.2f", userBalance.balance),
            color = Color.White
        )
    }
}