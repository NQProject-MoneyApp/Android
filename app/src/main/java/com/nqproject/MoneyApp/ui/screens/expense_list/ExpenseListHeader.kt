package com.nqproject.MoneyApp.ui.screens.expense_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R

@Composable
fun ExpenseListHeader(
    didPressBackButton: () -> Unit,
    didPressAddExpense: () -> Unit,
    body: @Composable () -> Unit,
    title: String) {

    Column() {
        TopAppBar(
            title = { Text(text = title, style = MaterialTheme.typography.h4) },
            backgroundColor = MaterialTheme.colors.background,
            navigationIcon = {
                IconButton(onClick = { didPressBackButton() }) {
                    Image(
                        painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "",
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        didPressAddExpense()
                    }) {
                    Image(
                        painterResource(id = R.drawable.ic_add),
                        contentDescription = "",
                    )
                }
            }
        )
        body()
    }
}