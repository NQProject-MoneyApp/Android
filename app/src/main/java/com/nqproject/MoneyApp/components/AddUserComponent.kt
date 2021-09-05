package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.theme.AppTheme


@Composable
fun AddUserComponent(user: User, didPressComponent: () -> Unit, check: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(horizontal = Config.MEDIUM_PADDING),

        ) {
        Text(user.name, color= AppTheme.colors.primaryText, modifier = Modifier.weight(1f))
        if (check) {
            IconButton(onClick = { didPressComponent() }) {
                Image(
                    painterResource(id = R.drawable.ic_cross),
                    contentDescription = "",
                )
            }
        } else {
            IconButton(onClick = { didPressComponent() }) {
                Image(
                    painterResource(id = R.drawable.ic_add_cross),
                    contentDescription = "",
                )
            }
        }
    }
}