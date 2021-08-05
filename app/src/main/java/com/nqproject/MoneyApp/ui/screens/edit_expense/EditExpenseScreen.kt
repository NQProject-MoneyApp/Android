package com.nqproject.MoneyApp.ui.screens.edit_expense

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseForm
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseHeader
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseViewModel
import kotlinx.coroutines.launch


@Composable
fun EditExpenseScreen(
    expense: Expense,
    onBackNavigate: () -> Unit,
    onDeleteExpenseNavigate: () -> Unit,
) {

    val viewModel = viewModel<AddExpenseViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value
    val context = LocalContext.current

    AddExpenseHeader(
        title = expense.name,
        didPressBackButton = onBackNavigate,
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AddExpenseForm(
                    defaultName = expense.name,
                    defaultAmount = expense.amount.toFloat(),
                    defaultParticipants = expense.participants,
                    loading = loading,
                    onSave = { name, amount, participants ->
                        Log.d(Config.MAIN_TAG, "on save edited expense: $name")
                        coroutineScope.launch {
                            when (val result =
                                viewModel.editExpense(
                                    expense, name = name, amount = amount,
                                    participants = participants
                                )) {
                                is SimpleResult.Error -> Toast
                                    .makeText(context, result.error, Toast.LENGTH_SHORT).show()
                                is SimpleResult.Success -> {
                                    SimpleResult.Success("Success")
                                    onBackNavigate()
                                }
                            }
                        }

                    }
                )

                Spacer(modifier = Modifier.height(21.dp))

                Button(
                    modifier = Modifier
                        .height(49.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error,
                    ),
                    enabled = !loading,
                    onClick = {
                        Log.d(Config.MAIN_TAG, "on delete expense: ${expense.name}")
                        coroutineScope.launch {
                            when (val result =
                                viewModel.deleteExpense(expense)) {
                                is SimpleResult.Error -> Toast.makeText(
                                    context,
                                    result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                                is SimpleResult.Success -> {
                                    SimpleResult.Success("Success")
                                    onDeleteExpenseNavigate()
                                }
                            }
                        }

                    }) {
                    Text("Delete", style = MaterialTheme.typography.h4)
                }
            }
        })
}

