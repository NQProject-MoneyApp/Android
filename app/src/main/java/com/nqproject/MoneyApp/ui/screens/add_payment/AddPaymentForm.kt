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
import com.nqproject.MoneyApp.components.DropdownInput
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.repository.User
import java.util.*

@Composable
fun AddPaymentForm(
    defaultAmount: Float? = null,
    defaultParticipants: List<User>? = null,
    loading: Boolean
) {
    val viewModel = viewModel<AddPaymentViewModel>()
    val groupMembers = viewModel.groupMembers.observeAsState().value!!

    val focusManager = LocalFocusManager.current
    val expenseAmountRequester by remember { mutableStateOf(FocusRequester()) }

    val expenseAmount = remember {
        ValidableValue(
            if (defaultAmount != null) String.format(Locale.US, "%.2f", defaultAmount) else "",
            validateFun = {
                val value = it.toFloatOrNull() ?: 0f
                when {
                    value == 0f -> "Enter an expense amount"
                    value < 0f -> "Expense amount must be greater than zero"
                    else -> ""
                }
            }
        )
    }

    val paidBy = remember {
        ValidableValue<Int?>(
            null,
            validateFun = {
                when (it) {
                    null -> "Enter expense payer"
                    else -> ""
                }
            }
        )
    }

    val paidTo = remember {
        ValidableValue<Int?>(
            null,
            validateFun = {
                when (it) {
                    null -> "Enter expense receiver"
                    else -> ""
                }
            }
        )
    }

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

    DropdownInput(
        label = "From",
        selectedIndex = 0,
        options = groupMembers.map { it.name },
        onChanged = {  }
    )

    Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))

    DropdownInput(
        label = "To",
        selectedIndex = 0,
        options = groupMembers.map { it.name },
        onChanged = {  }
    )

    Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(StyleConfig.XLARGE_PADDING),
        shape = RoundedCornerShape(StyleConfig.XSMALL_PADDING),
        enabled = !loading,
        onClick = {
            expenseAmount.validate()
            if (!expenseAmount.isError()) {
                //TODO
            }
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }
}