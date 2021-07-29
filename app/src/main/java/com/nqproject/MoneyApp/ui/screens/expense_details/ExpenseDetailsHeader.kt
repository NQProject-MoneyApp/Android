package com.nqproject.MoneyApp.ui.screens.expense_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R

@Composable
fun ExpenseDetailsHeader(didPressBackButton: () -> Unit, didPressOptions: () -> Unit, body: @Composable () -> Unit, title: String) {

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
                    onClick = { didPressOptions() }) {
                    Image(
                        painterResource(id = R.drawable.ic_menu),
                        contentDescription = "",
                    )
                }
            }
        )
        body()
    }
}