package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.User


@Composable
fun AddUserComponent(user: User, didPressComponent: () -> Unit, check: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(horizontal = 24.dp),

        ) {
        Text(user.name, color= Color.White, modifier = Modifier.weight(1f))
        if (check) {
            Image(
                painterResource(id = R.drawable.ic_cross),
                modifier = Modifier
                    .clickable { didPressComponent() },
                contentDescription = "",
            )
        } else {
            Image(
                painterResource(id = R.drawable.ic_add_cross),
                modifier = Modifier
                    .clickable { didPressComponent() },
                contentDescription = "",
            )
        }
    }
}
