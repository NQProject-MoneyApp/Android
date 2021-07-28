package com.nqproject.MoneyApp.ui.screens.expense_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.screens.Header

@Composable
fun ExpenseListHeader(didPressBackButton: () -> Unit, didPressAddExpense: () -> Unit, body: @Composable () -> Unit, title: String) {
    var showMenu by remember { mutableStateOf(false) }

    Header(title = title,
        leftIcon = {
            Image(
                painterResource(id = R.drawable.ic_back_arrow),
                modifier = Modifier
                    .clickable { didPressBackButton() },
                contentDescription = "",
            )
        }, rightIcon = {
            Row() {

                Image(
                    painterResource(id = R.drawable.ic_menu),
                    modifier = Modifier
                        .clickable { showMenu = !showMenu
                        },
                    contentDescription = "",
                )

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        didPressAddExpense()
                        showMenu = false
                    }) {
                        Text("Add Expense", color = MaterialTheme.colors.primary)
                    }
                }
            }
        }, body = body)
}