package com.nqproject.MoneyApp.ui.screens.expense_details

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Expense
import java.util.*

@Composable
fun ExpenseDetailsScreen(navController: NavController, expense: Expense) {

    ExpenseDetailsHeader(
        didPressBackButton = {
            Log.d(Config.MAIN_TAG, "didPressBackButton")
            navController.popBackStack()
        },
        didPressOptions = { Log.d(Config.MAIN_TAG, "didPressOptions") },
        body = {
               Card(
                   backgroundColor = MaterialTheme.colors.secondary,
                   shape = RoundedCornerShape(15),
                   modifier = Modifier
                       .fillMaxWidth()
               ) {
                   Column {
                       Row {
                           Text(
                               modifier = Modifier.weight(1f),
                               text = "Amount",
                               color = Color.White,
                               style = MaterialTheme.typography.h4
                           )
                           Text(
                               text = String.format(Locale.US, "%.2f", expense.amount),
                               color = Color.White,
                               style = MaterialTheme.typography.h4
                           )
                       }
                       Row {
                           Text(
                               modifier = Modifier.weight(1f),
                               text = "Paid by",
                               color = Color.White,
                               style = MaterialTheme.typography.h4
                           )
                           Text(
                               text = "${expense.author}",
                               color = Color.White,
                               style = MaterialTheme.typography.h4
                           )
                       }
                   }
               }
                Text(
                    text = "Created on\n ${expense.createDate}",
                    color = Color.White
                )
        },
        title = expense.name
    )
}
