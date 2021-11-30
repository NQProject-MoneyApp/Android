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
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.network.models.ExpenseType
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
            Log.d(StyleConfig.MAIN_TAG, "didPressBackButton")
            onBackNavigate()
        },
        didPressEditExpense = {
            Log.d(StyleConfig.MAIN_TAG, "didPressEditExpense")
            onEditExpenseNavigate()
        },
        body = {
            if (expense != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(StyleConfig.MEDIUM_PADDING),
                ) {
                    Card(
                        backgroundColor = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(StyleConfig.MEDIUM_PADDING)
                        ) {
                            Text(
                                expense!!.type.toString(),
                                color = AppTheme.colors.primaryText,
                                style = MaterialTheme.typography.h4
                            )

                            Spacer(modifier = Modifier.height(StyleConfig.SMALL_PADDING))
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
                                    text = expense!!.paidBy,
                                    color = AppTheme.colors.primaryText,
                                    style = MaterialTheme.typography.h4
                                )
                            }
                            if (expense!!.type == ExpenseType.Payment) {
                                Row {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Paid to",
                                        color = AppTheme.colors.primaryText,
                                        style = MaterialTheme.typography.h4
                                    )
                                    Text(
                                        text = expense!!.paymentTo!!,
                                        color = AppTheme.colors.primaryText,
                                        style = MaterialTheme.typography.h4
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(StyleConfig.LARGE_PADDING))

                    if (expense!!.type == ExpenseType.Expense) {

                        Card(
                            backgroundColor = MaterialTheme.colors.secondary,
                            shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(StyleConfig.SMALL_PADDING)
                            ) {
                                Text("Participants", color = AppTheme.colors.primaryText)
                                Spacer(modifier = Modifier.height(StyleConfig.SMALL_PADDING))


                                expense!!.participants.forEach {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.padding(
                                            horizontal = StyleConfig.MEDIUM_PADDING
                                        ),
                                    ) {
                                        Text(
                                            it.name, color = AppTheme.colors.primaryText,
                                            modifier = Modifier
                                                .weight(1f)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(StyleConfig.XSMALL_PADDING))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(StyleConfig.LARGE_PADDING))
                    }
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
                    Spacer(modifier = Modifier.height(StyleConfig.SMALL_PADDING))
                    Text(
                        text = "Created by\n ${expense!!.author.name}",
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
