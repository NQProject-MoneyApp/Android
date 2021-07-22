package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.screens.Header

@Composable
fun GroupDetailsHeader(didPressBackButton: () -> Unit, didPressOptions: () -> Unit, body: @Composable () -> Unit, title: String) {
    Header(title = title,
        leftIcon = {
            Image(
                painterResource(id = R.drawable.ic_back_arrow),
                modifier = Modifier
                    .clickable { didPressBackButton() },
                contentDescription = "",
            )
        }, rightIcon = {
            Image(
                painterResource(id = R.drawable.ic_menu),
                modifier = Modifier
                    .clickable { didPressOptions() },
                contentDescription = "",
            )}, body = body)
}