package com.nqproject.MoneyApp.ui.screens.expense_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.util.*

@Composable
fun ExpenseListComponent(expense: Expense, didPressComponent: (Expense) -> Unit) {

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(Config.ROUNDED_CORNERS),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { didPressComponent(expense) }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(Config.SMALL_PADDING + Config.XSMALL_PADDING)
        ) {
            Text(
                text = expense.name,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary
            )
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = expense.author.name,
                    style = MaterialTheme.typography.h5,
                    color = AppTheme.colors.primaryText
                )
                Text(
                    text = "\$${String.format(Locale.US, "%.2f", expense.amount)}",
                    style = MaterialTheme.typography.h5,
                    color = AppTheme.colors.primaryText
                )
            }
        }
    }
}