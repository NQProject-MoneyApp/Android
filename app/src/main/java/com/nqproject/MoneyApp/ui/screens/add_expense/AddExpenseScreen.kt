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
        didPressMenuButton = {
            Log.d(Config.MAIN_TAG, "didPressMenuButton")

        }, body = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,) {

                ExpenseNameForm(
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

@Composable
private fun ExpenseNameForm(onSave: (name: String, amount: Float) -> Unit, loading: Boolean) {
    val expenseName = remember { mutableStateOf("") }
    val expenseAmount = remember { mutableStateOf("0") }

    val expenseAmountRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    Spacer(modifier = Modifier.height(21.dp))

    InputField(
        focusRequester = FocusRequester(),
        fieldState = expenseName,
        keyboardType = KeyboardType.Number,
        placeholder = "Name",
        focusRequesterAction = {
            expenseAmountRequester.requestFocus()
        })

    Spacer(modifier = Modifier.height(21.dp))

    InputField(
        focusRequester = FocusRequester(),
        fieldState = expenseAmount,
        keyboardType = KeyboardType.Number,
        placeholder = "Amount",
        focusRequesterAction = {
            focusManager.clearFocus()
        })

    Spacer(modifier = Modifier.height(21.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            onSave(expenseName.value, expenseAmount.value.toFloat())
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}
//
//@Composable
//private fun InputField(expenseName: MutableState<String>) {
//
//    TextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        value = expenseName.value,
//        onValueChange = { newValue ->
//            expenseName.value = newValue
//        },
//        label = { Text("Expense name", color = AppTheme.colors.hintText) },
//        shape = RoundedCornerShape(10.dp),
//        singleLine = true,
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = MaterialTheme.colors.surface,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent
//        ),
//    )
//}


