package com.nqproject.MoneyApp.ui.screens.about

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun CreatorComponent(name: String, email: String) {
    Text(name, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.body1)
    Text(email, color = AppTheme.colors.hintText, style = MaterialTheme.typography.body1)
    Spacer(modifier = Modifier.height(Config.MEDIUM_PADDING))
}