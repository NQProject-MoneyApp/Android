package com.nqproject.MoneyApp.ui.screens.suggested_payment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.SuggestedPayment
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.util.*


@Composable
fun SuggestedPaymentComponent(payment: SuggestedPayment, onSave: (SuggestedPayment) -> Unit) {

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(StyleConfig.SMALL_PADDING + StyleConfig.XSMALL_PADDING)
        ) {
            Text(
                text = "From: ${payment.paidBy.name}",
                style = MaterialTheme.typography.h4,
                color = AppTheme.colors.primaryText
            )
            Text(
                text = "To: ${payment.paidTo.name}",
                style = MaterialTheme.typography.h4,
                color = AppTheme.colors.primaryText
            )
            Text(
                text = "Amount: $${String.format(
                    Locale.US, "%.2f", payment.amount)}",
                style = MaterialTheme.typography.h4,
                color = AppTheme.colors.primaryText
            )
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                onSave(payment)
            }) {
                Text(
                    "Save the payment",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}