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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.repository.Expense
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExpenseListComponent(expense: Expense) {
    val dateFormat = SimpleDateFormat("dd-MM-yyy")

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .fillMaxWidth()
            //.clickable { didPressComponent(expense) }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            //horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = expense.name,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary
            )
            Row() {
                Text(
                    modifier = Modifier.weight(1f),
                    text = expense.author.name,
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
                Text(
                    text = "\$${String.format(Locale.US, "%.2f", expense.amount)}",
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
            }
        }
    }
}