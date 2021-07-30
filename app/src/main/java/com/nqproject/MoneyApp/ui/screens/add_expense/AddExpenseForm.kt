package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.ui.screens.auth.InputField

@Composable
fun AddExpenseForm(onSave: (name: String, amount: Float) -> Unit, loading: Boolean) {
    val expenseName = remember { mutableStateOf("") }
    val expenseAmount = remember { mutableStateOf("0") }

    val expenseAmountRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Spacer(modifier = Modifier.height(21.dp))

    InputField(
        focusRequester = FocusRequester(),
        fieldState = expenseName,
        keyboardType = KeyboardType.Text,
        placeholder = "Name",
        focusRequesterAction = {
            expenseAmountRequester.requestFocus()
        })

    Spacer(modifier = Modifier.height(21.dp))

    InputField(
        focusRequester = expenseAmountRequester,
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
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            onSave(expenseName.value, expenseAmount.value.toFloatOrNull() ?: 0f)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}