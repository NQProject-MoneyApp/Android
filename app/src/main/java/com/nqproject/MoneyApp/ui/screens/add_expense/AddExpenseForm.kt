package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.components.ChooseUsersComponent
import com.nqproject.MoneyApp.components.DropdownInput
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.repository.User
import java.util.*


@Composable
fun AddExpenseForm(
    defaultName: String = "",
    defaultAmount: Float? = null,
    defaultPaidBy: String? = null,
    defaultParticipants: List<User>? = null,
    onSave: (name: String, amount: Float, paidBy: Int, participants: List<User>) -> Unit,
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
    var defaultPaidByIndex: Int? = groupMembers.indexOfFirst { it.name == defaultPaidBy }
    if (defaultPaidByIndex == -1) {
        defaultPaidByIndex = null
    }
    val expensePaidBy = remember {
        ValidableValue(
            defaultPaidByIndex,
            {
                when (it) {
                    null -> "Enter expense payer"
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

    val expenseNameValue = expenseName.value.collectAsState().value
    val expenseAmountValue = expenseAmount.value.collectAsState().value
    val expenseParticipantsValue = expenseParticipants.value.collectAsState().value
    val expensePaidByValue = expensePaidBy.value.collectAsState().value
    val expensePaidByError = expensePaidBy.errorMessage.collectAsState().value

    Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))

    InputField(
        focusRequester = expenseNameRequester,
        fieldState = expenseName,
        keyboardType = KeyboardType.Text,
        placeholder = "Name",
        focusRequesterAction = {
            expenseAmountRequester.requestFocus()
        },
    )

    InputField(
        focusRequester = expenseAmountRequester,
        fieldState = expenseAmount,
        keyboardType = KeyboardType.Number,
        placeholder = "Amount",
        focusRequesterAction = {
            focusManager.clearFocus()
        },
    )

    DropdownInput(
        label = "Paid by",
        selectedIndex = expensePaidByValue,
        options = groupMembers.map { it.name },
        onChanged = { expensePaidBy.updateValue(it) }
    )

    Text(
        text = expensePaidByError,
        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
    )

    ChooseUsersComponent(
        title = "Participants",
        groupMembers = groupMembers,
        chosenMembers = expenseParticipants,
    )

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(StyleConfig.XLARGE_PADDING),
        shape = RoundedCornerShape(StyleConfig.XSMALL_PADDING),
        enabled = !loading,
        onClick = {
            expenseName.validate()
            expenseAmount.validate()
            expenseParticipants.validate()
            expensePaidBy.validate()
            if (!expenseName.isError() && !expenseAmount.isError() && !expenseParticipants.isError() && !expensePaidBy.isError())
                onSave(
                    expenseNameValue, expenseAmountValue.toFloatOrNull() ?: 0f,
                    groupMembers[expensePaidByValue!!].pk,
                    expenseParticipantsValue
                )
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}