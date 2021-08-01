package com.nqproject.MoneyApp.ui.screens.edit_expense

import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.ExpenseDetails
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseForm
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseHeader
import kotlinx.coroutines.launch


@Composable
fun EditExpenseScreen(
    groupId: Int,
    expense: ExpenseDetails,
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<EditExpenseViewModel>()
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
                    loading = loading,
                    onSave = { name, amount ->
                        Log.d(Config.MAIN_TAG, "on save edited expense: $name")
                        coroutineScope.launch {
                            when (val result =
                                viewModel.editExpense(
                                    groupId, expense.pk, name = name, amount = amount
                                )) {
                                is SimpleResult.Error -> Toast.makeText(
                                    context,
                                    result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                                is SimpleResult.Success -> {
                                    SimpleResult.Success("Success")
                                    onBackNavigate()
                                }
                            }
                        }

                    }
                )
            }
        })
}

