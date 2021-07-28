package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.screens.Header


@Composable
fun AddExpenseHeader(didPressBackButton: () -> Unit, didPressMenuButton: () -> Unit,body: @Composable () -> Unit) {
    Header(title = "New Expense",
        leftIcon = {
            Image(
                painterResource(id = R.drawable.ic_back_arrow),
                modifier = Modifier
                    .clickable { didPressBackButton() },
                contentDescription = "",
            )
        },
        rightIcon = {
            Image(
                painterResource(id = R.drawable.ic_menu),
                modifier = Modifier
                    .clickable { didPressMenuButton() },
                contentDescription = "",
            )
        },
        body = body )
}