package com.nqproject.MoneyApp.ui.screens.add_payment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.launch

@Composable
fun AddPaymentScreen (
    groupId: Int,
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddPaymentViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value

    val context = LocalContext.current

    AddPaymentHeader(
    title = "New payment",
    didPressBackButton = onBackNavigate,
    body = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = StyleConfig.MEDIUM_PADDING),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AddPaymentForm(
                loading = loading,
                onSave = { name, amount, participants ->
                    Log.d(StyleConfig.MAIN_TAG, "on save payment: $name")
                    coroutineScope.launch {
                        when (val result =
                            viewModel.addExpense(groupId, name = name, amount = amount,
                                participants = participants)) {
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