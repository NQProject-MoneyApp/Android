package com.nqproject.MoneyApp.ui.screens.add_expense

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.ui.screens.add_group.ImageAlertComponent
import com.nqproject.MoneyApp.ui.screens.auth.InputField
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun AddExpenseScreen(
    groupId: Int,
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddExpenseViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value
    val context = LocalContext.current

    AddExpenseHeader(
        didPressBackButton = onBackNavigate,
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,) {

                AddExpenseForm(
                    loading=loading,
                    onSave = { name, amount ->
                        when {
                            name.isEmpty() -> {
                                Toast.makeText(context, "Enter an expense name", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            amount == 0f -> {
                                Toast.makeText(context, "Enter an expense amount", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Log.d(Config.MAIN_TAG, "on save expense: $name")
                                coroutineScope.launch {
                                    val result = viewModel.addExpense(groupId, name=name, amount=amount)

                                    when(result) {
                                        is SimpleResult.Error -> Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                                        is SimpleResult.Success -> {
                                            SimpleResult.Success("Success")
                                            onBackNavigate()
                                        }
                                    }
                                }
                            }
                        }

                    }
                )
            }
        })
}

