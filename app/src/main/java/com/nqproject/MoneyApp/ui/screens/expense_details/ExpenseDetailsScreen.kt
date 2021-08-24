package com.nqproject.MoneyApp.ui.screens.expense_details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun ExpenseDetailsScreen(
    onBackNavigate: () -> Unit,
    onEditExpenseNavigate: () -> Unit,
) {

    val viewModel = viewModel<ExpenseDetailsViewModel>()
    val dateFormat = SimpleDateFormat("dd-MM-yyy")
    val scrollState = rememberScrollState()
    val expense by viewModel.expense.observeAsState()

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
            if(expense != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(32.dp),
                ) {
                    Card(
                        backgroundColor = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(10),
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
                                    color = AppTheme.colors.primaryText,
                                    style = MaterialTheme.typography.h4
                                )
                                Text(
                                    text = String.format(
                                        Locale.US, "%.2f", expense!!.amount
                                    ),
                                    color = AppTheme.colors.primaryText,
                                    style = MaterialTheme.typography.h4
                                )
                            }
                            Row {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Paid by",
                                    color = AppTheme.colors.primaryText,
                                    style = MaterialTheme.typography.h4
                                )
                                Text(
                                    text = expense!!.author.name,
                                    color = AppTheme.colors.primaryText,
                                    style = MaterialTheme.typography.h4
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Card(
                        backgroundColor = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(10),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Participants", color = AppTheme.colors.primaryText)
                            Spacer(modifier = Modifier.height(16.dp))


                            expense!!.participants.forEach {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                ) {
                                    Text(
                                        it.name, color = AppTheme.colors.primaryText, modifier = Modifier
                                            .weight(1f)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Created on\n ${
                            dateFormat.format(
                                expense!!.createDate
                            )
                        }",
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }
        },
        title = expense?.name ?: ""
    )
}
