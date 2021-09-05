package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.util.*

@Composable
fun GroupBalanceComponent(user: User) {

    Row {
        Text(
            modifier = Modifier.weight(1f),
            text = user.name,
            color = AppTheme.colors.primaryText
        )
        Text(String.format(Locale.US, "%.2f", user.balance),
            color = AppTheme.colors.primaryText
        )
    }
}