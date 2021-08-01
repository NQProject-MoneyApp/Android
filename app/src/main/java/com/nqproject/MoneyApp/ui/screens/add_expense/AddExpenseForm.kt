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
import com.nqproject.MoneyApp.ui.screens.auth.InputFieldValidator
import androidx.compose.runtime.*


@Composable
fun AddExpenseForm(
    defaultName: String = "",
    defaultAmount: Float? = null,
    onSave: (name: String, amount: Float) -> Unit,
    loading: Boolean
) {
    val expenseName = remember { mutableStateOf(defaultName) }
    val expenseAmount = remember { mutableStateOf(defaultAmount?.toString() ?: "" ) }

    val expenseNameRequester by remember { mutableStateOf(FocusRequester()) }
    val expenseAmountRequester by remember { mutableStateOf(FocusRequester()) }

    val focusManager = LocalFocusManager.current

    val nameValidator by remember {
        mutableStateOf(InputFieldValidator {
            when {
                it.isEmpty() -> "Enter an expense name"
                else -> ""
            }
        })
    }

    val amountValidator by remember {
        mutableStateOf(InputFieldValidator {
            val value = it.toFloatOrNull() ?: 0f
            when {
                value == 0f -> "Enter an expense amount"
                value < 0f -> "Expense amount must be greater than zero"
                else -> ""
            }
        })
    }

    Spacer(modifier = Modifier.height(21.dp))

    InputField(
        focusRequester = expenseNameRequester,
        fieldState = expenseName,
        keyboardType = KeyboardType.Text,
        placeholder = "Name",
        focusRequesterAction = {
            expenseAmountRequester.requestFocus()
        },
        validator = nameValidator
    )

    Spacer(modifier = Modifier.height(5.dp))

    InputField(
        focusRequester = expenseAmountRequester,
        fieldState = expenseAmount,
        keyboardType = KeyboardType.Number,
        placeholder = "Amount",
        focusRequesterAction = {
            focusManager.clearFocus()
        },
        validator = amountValidator
    )

    Spacer(modifier = Modifier.height(5.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            nameValidator.validate(expenseName.value)
            amountValidator.validate(expenseAmount.value)
            if (!nameValidator.isError() and !amountValidator.isError())
                onSave(expenseName.value, expenseAmount.value.toFloatOrNull() ?: 0f)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}