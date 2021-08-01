package com.nqproject.MoneyApp.ui.screens.edit_expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R

@Composable
fun EditExpenseHeader (
    didPressBackButton: () -> Unit,
    didPressEditExpense: () -> Unit,
    body: @Composable () -> Unit) {

    var showMenu by remember { mutableStateOf(false) }


    Column() {
        TopAppBar(
            title = { Text(text = "New Expense", style = MaterialTheme.typography.h4) },
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
                    onClick = { showMenu = true }) {
                    Image(
                        painterResource(id = R.drawable.ic_menu),
                        contentDescription = "",
                    )

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            didPressEditExpense()
                            showMenu = false
                        }) {
                            Text("Edit", color = MaterialTheme.colors.primary)
                        }
                    }
                }
            }
        )
        body()
    }
}