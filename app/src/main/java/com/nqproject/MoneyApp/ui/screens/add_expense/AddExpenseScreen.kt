package com.nqproject.MoneyApp.ui.screens.add_expense

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.launch


@Composable
fun AddExpenseScreen(
    groupId: Int,
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddExpenseViewModel>()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value

    val context = LocalContext.current

    AddExpenseHeader(
        title = "New expense",
        didPressBackButton = onBackNavigate,
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = StyleConfig.MEDIUM_PADDING),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AddExpenseForm(
                    loading = loading,
                    onSave = { name, amount, paidBy, participants ->
                        Log.d(StyleConfig.MAIN_TAG, "on save expense: $name")
                        coroutineScope.launch {
                            when (val result =
                                viewModel.addExpense(groupId, name = name, amount = amount,
                                    participants = participants, paidById = paidBy)) {
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

