package com.nqproject.MoneyApp.ui.screens.auth

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.ui.theme.AppTheme


@Composable
fun BottomOption(text: String, buttonText: String, onClick: () -> Unit) {
    Row(

        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.width(4.dp))
        TextButton(
            modifier = Modifier.height(39.dp),
            onClick = onClick
        ) {
            Text(buttonText, style = MaterialTheme.typography.h5, color = AppTheme.colors.primary)
        }

    }
}