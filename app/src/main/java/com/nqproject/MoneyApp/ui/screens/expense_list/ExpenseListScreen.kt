package com.nqproject.MoneyApp.ui.screens.expense_list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Expense

@Composable
fun ExpenseListScreen(
    onBackNavigate: () -> Unit,
    onExpenseDetailsNavigate: (expense: Expense) -> Unit,
    onAddExpenseNavigate: () -> Unit,
) {

    val viewModel = viewModel<ExpenseListViewModel>()
    val scrollState = rememberScrollState()
    val groupExpenses = viewModel.groupExpenses.observeAsState(emptyList()).value
    val isRefreshing by viewModel.loading.observeAsState(false)

    ExpenseListHeader(
        didPressBackButton = {
            Log.d(Config.MAIN_TAG, "didPressBackButton")
            onBackNavigate()
        },
        didPressAddExpense = {
            onAddExpenseNavigate()
        },
        body = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    viewModel.updateExpenses()
                },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.background,
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    if (groupExpenses.isEmpty()) {
                        Text("You don't have any expenses", style = MaterialTheme.typography.h4)
                        Spacer(modifier = Modifier.height(21.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(49.dp)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                onAddExpenseNavigate()
                            }) {
                            Text("Add", style = MaterialTheme.typography.h4)
                        }
                    } else {
                        groupExpenses.forEach { it ->
                            ExpenseListComponent(it,
                                didPressComponent = {
                                    onExpenseDetailsNavigate(it)
                                })
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        },
        title = "Expenses")
}