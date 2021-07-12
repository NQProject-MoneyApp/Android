package com.nqproject.MoneyApp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Header(title: String, leftIcon: @Composable () -> Unit, rightIcon: @Composable () -> Unit, body: @Composable () -> Unit) {
    Column() {
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(10.dp))
            leftIcon()
            Spacer(modifier = Modifier.width(10.dp))
            Text(title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h4)
            rightIcon()
            Spacer(modifier = Modifier.width(10.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        body()
    }
}