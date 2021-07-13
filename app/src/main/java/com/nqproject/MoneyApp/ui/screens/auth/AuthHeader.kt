package com.nqproject.MoneyApp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.R


@Composable
fun AuthHeader() {
    Spacer(modifier = Modifier.height(39.dp))
    Image(
        painterResource(id = R.drawable.ic_icon),
        modifier = Modifier.size(132.dp),
        contentDescription = ""
    )
    Spacer(modifier = Modifier.height(69.dp))

    Text(
        text = "Hello",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.primary,)
    Spacer(modifier = Modifier.height(40.dp))
}