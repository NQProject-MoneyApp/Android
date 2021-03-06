package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R


@Composable
fun AddExpenseHeader(
    title: String,
    didPressBackButton: () -> Unit,
    body: @Composable () -> Unit
) {

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
            }
        )
        body()
    }
}