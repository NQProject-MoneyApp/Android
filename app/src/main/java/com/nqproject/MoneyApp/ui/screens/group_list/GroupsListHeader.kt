package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.ui.screens.Header
import com.nqproject.MoneyApp.R

@Composable
fun GroupListHeader(didPressUserButton: () -> Unit, didPressAddGroup: () -> Unit, body: @Composable () -> Unit) {
    Header(title = "Groups",
        leftIcon = {
            Image(
                painterResource(id = R.drawable.ic_user),
                modifier = Modifier
                    .clickable { didPressUserButton() },
                contentDescription = "",
            )
    }, rightIcon = {
        Image(
            painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .clickable { didPressAddGroup() },
            contentDescription = "",
        )}, body = body)
}