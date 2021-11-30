package com.nqproject.MoneyApp.ui.screens.suggested_payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.R


@Composable
fun SuggesterPaymentHeader(didPressBackButton: () -> Unit, body: @Composable () -> Unit) {



    Column() {
        TopAppBar(
            title = { Text(text = "Suggested Payments", style = MaterialTheme.typography.h4) },
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


            }
        )
        body()
    }
}