package com.nqproject.MoneyApp.ui.screens.expense_details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Expense
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun ExpenseDetailsScreen(
    onBackNavigate: () -> Unit,
    onEditExpenseNavigate: () -> Unit,
    expense: Expense,
) {

    val dateFormat = SimpleDateFormat("dd-MM-yyy")

    ExpenseDetailsHeader(
        didPressBackButton = {
            Log.d(Config.MAIN_TAG, "didPressBackButton")
            onBackNavigate()
        },
        didPressEditExpense = {
            Log.d(Config.MAIN_TAG, "didPressEditExpense")
            onEditExpenseNavigate()
        },
        body = {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                    ) {
                Card(
                    backgroundColor = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(24.dp)
                    ) {
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
                                text = expense.author.name,
                                color = Color.White,
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Created on\n ${dateFormat.format(expense.createDate)}",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        title = expense.name
    )
}
