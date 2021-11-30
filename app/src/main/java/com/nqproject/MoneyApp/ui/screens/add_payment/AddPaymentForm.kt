package com.nqproject.MoneyApp.ui.screens.add_payment

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.repository.User
import java.util.*

@Composable
fun AddPaymentForm(
    defaultAmount: Float? = null,
    defaultParticipants: List<User>? = null,
    onSave: (name: String, amount: Float, participants: List<User>) -> Unit,
    loading: Boolean
) {
    val viewModel = viewModel<AddPaymentViewModel>()
    val groupMembers = viewModel.groupMembers.observeAsState().value!!

    val focusManager = LocalFocusManager.current
    val expenseAmountRequester by remember { mutableStateOf(FocusRequester()) }

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

    val expenseAmountValue = expenseAmount.value.collectAsState().value
    val expenseParticipantsValue = expenseParticipants.value.collectAsState().value

    Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))

    InputField(
    focusRequester = expenseAmountRequester,
    fieldState = expenseAmount,
    keyboardType = KeyboardType.Number,
    placeholder = "Amount",
    focusRequesterAction = {
        focusManager.clearFocus()
    },
    )

    Button(
    modifier = Modifier
    .fillMaxWidth()
    .height(StyleConfig.XLARGE_PADDING),
    shape = RoundedCornerShape(StyleConfig.XSMALL_PADDING),
    enabled = !loading,
    onClick = {
        expenseAmount.validate()
        expenseParticipants.validate()
        if (!expenseAmount.isError() and !expenseParticipants.isError())
            onSave(
                "payment", expenseAmountValue.toFloatOrNull() ?: 0f,
                expenseParticipantsValue
            )
    }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }
}