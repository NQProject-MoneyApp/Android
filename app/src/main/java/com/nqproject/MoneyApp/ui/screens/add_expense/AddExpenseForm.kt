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
import java.util.*


@Composable
fun AddExpenseForm(
    defaultName: String = "",
    defaultAmount: Float? = null,
    onSave: (name: String, amount: Float) -> Unit,
    loading: Boolean
) {
    val viewModel = viewModel<AddExpenseViewModel>()
    val chosenMembers = viewModel.chosenParticipants.observeAsState(emptyList()).value
    val groupMembers = viewModel.groupMembers.observeAsState(emptyList()).value

    val expenseName = remember { mutableStateOf(defaultName) }
    val expenseAmount = remember {
        mutableStateOf(
            if (defaultAmount != null) String.format(Locale.US, "%.2f", defaultAmount)
            else ""
        )
    }

    val expenseNameRequester by remember { mutableStateOf(FocusRequester()) }
    val expenseAmountRequester by remember { mutableStateOf(FocusRequester()) }

    val focusManager = LocalFocusManager.current

    val nameValidator by remember {
        mutableStateOf(InputFieldValidator<String> {
            when {
                it.isEmpty() -> "Enter an expense name"
                else -> ""
            }
        })
    }

    val amountValidator by remember {
        mutableStateOf(InputFieldValidator<String> {
            val value = it.toFloatOrNull() ?: 0f
            when {
                value == 0f -> "Enter an expense amount"
                value < 0f -> "Expense amount must be greater than zero"
                else -> ""
            }
        })
    }

    val usersValidator by remember {
        mutableStateOf(InputFieldValidator<List<User>> {
            when {
                it.isEmpty() -> "Choose expense participants"
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

    ChooseUsersComponent(
        title = "Participants",
        groupMembers = groupMembers,
        chosenMembers = chosenMembers,
        onAddUser = { viewModel.addChosenMember(it) },
        onRemoveUser = { viewModel.removeChosenMember(it) },
        validator = usersValidator,
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
            usersValidator.validate(chosenMembers)
            if (!nameValidator.isError() and !amountValidator.isError() and !usersValidator.isError())
                onSave(expenseName.value, expenseAmount.value.toFloatOrNull() ?: 0f)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}