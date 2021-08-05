package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.components.ChooseUsersComponent
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.screens.auth.ValidableValue
import java.util.*


@Composable
fun AddExpenseForm(
    defaultName: String = "",
    defaultAmount: Float? = null,
    defaultParticipants: List<User>? = null,
    onSave: (name: String, amount: Float, participants: List<User>) -> Unit,
    loading: Boolean
) {
    val viewModel = viewModel<AddExpenseViewModel>()
    val groupMembers = viewModel.groupMembers.observeAsState().value!!

    val focusManager = LocalFocusManager.current
    val expenseNameRequester by remember { mutableStateOf(FocusRequester()) }
    val expenseAmountRequester by remember { mutableStateOf(FocusRequester()) }

    val expenseName = remember {
        ValidableValue(defaultName,
            {
                when {
                    it.isEmpty() -> "Enter an expense name"
                    else -> ""
                }
            }
        )
    }
    val expenseAmount = remember {
        ValidableValue(
            if (defaultAmount != null) String.format(Locale.US, "%.2f", defaultAmount) else "",
            {
                val value = it.toFloatOrNull() ?: 0f
                when {
                    value == 0f -> "Enter an expense amount"
                    value < 0f -> "Expense amount must be greater than zero"
                    else -> ""
                }
            }
        )
    }
    val expenseParticipants = remember {
        ValidableValue(defaultParticipants ?: groupMembers,
            {
                when {
                    it.isEmpty() -> "Choose expense participants"
                    else -> ""
                }
            }
        )
    }

    val expenseNameValue = expenseName.value.observeAsState().value!!
    val expenseAmountValue = expenseAmount.value.observeAsState().value!!
    val expenseParticipantsValue = expenseParticipants.value.observeAsState().value!!

    Spacer(modifier = Modifier.height(21.dp))

    InputField(
        focusRequester = expenseNameRequester,
        fieldState = expenseName,
        keyboardType = KeyboardType.Text,
        placeholder = "Name",
        focusRequesterAction = {
            expenseAmountRequester.requestFocus()
        },
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
    )

    Spacer(modifier = Modifier.height(5.dp))

    ChooseUsersComponent(
        title = "Participants",
        groupMembers = groupMembers,
        chosenMembers = expenseParticipants,
    )


    Spacer(modifier = Modifier.height(5.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            expenseName.validate()
            expenseAmount.validate()
            expenseParticipants.validate()
            if (!expenseName.isError() and !expenseAmount.isError() and !expenseParticipants.isError())
                onSave(
                    expenseNameValue, expenseAmountValue.toFloatOrNull() ?: 0f,
                    expenseParticipantsValue
                )
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}