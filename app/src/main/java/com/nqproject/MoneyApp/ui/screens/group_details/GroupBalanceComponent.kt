package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color

@Composable
fun GroupBalanceComponent(userBalance: UserBalance) {

    Row {
        Text(
            modifier = Modifier.weight(1f),
            text = userBalance.name,
            color = Color.White
        )
        Text(userBalance.balance.toString(),
            color = Color.White
        )
    }
}