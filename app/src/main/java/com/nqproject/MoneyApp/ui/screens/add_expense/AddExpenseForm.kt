package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect.Companion.Zero
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.Size.Companion.Zero
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.components.ChooseUsersComponent
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.util.*


@Composable
fun AddExpenseForm(
    defaultName: String = "",
    defaultAmount: Float? = null,
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

    val expensePaidBy = remember {
        ValidableValue<Int?>(
            null,
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

    var expanded by remember { mutableStateOf(false) }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val cardShape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS)


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            backgroundColor = MaterialTheme.colors.secondary,
            shape = cardShape,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .clipToBounds()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(StyleConfig.SMALL_PADDING)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        },
                    text = if (expensePaidByValue != null) groupMembers[expensePaidByValue].name else
                        "Paid by",
                    color = if (expensePaidByValue != null) AppTheme.colors.primaryText else AppTheme.colors.hintText,
                    style = MaterialTheme.typography.h5
                )
            }
        }
        Text(
            text = expensePaidBy.errorMessage.value,
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(StyleConfig.SMALL_PADDING)
                .fillMaxWidth()
        ) {
            groupMembers.forEachIndexed { index, p ->
                DropdownMenuItem(onClick = {
                    expensePaidBy.updateValue(index)
                }) {
                    Text(
                        text = p.name,
                        color = AppTheme.colors.primaryText,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
    }

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
                    expensePaidByValue!!,
                    expenseParticipantsValue
                )
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}