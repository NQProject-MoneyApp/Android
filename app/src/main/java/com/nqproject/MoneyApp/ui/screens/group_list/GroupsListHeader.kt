package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.ui.screens.Header
import com.nqproject.MoneyApp.R

@Composable
fun GroupListHeader(didPressUserButton: () -> Unit, didPressAddGroup: () -> Unit, didPressJoinGroup: () -> Unit, body: @Composable () -> Unit) {

    var showMenu by remember { mutableStateOf(false) }

    Header(title = "Groups",
        leftIcon = {
            Image(
                painterResource(id = R.drawable.ic_user),
                modifier = Modifier
                    .clickable { didPressUserButton() },
                contentDescription = "",
            )
    }, rightIcon = {

    Row() {

        Image(
            painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .clickable { showMenu = !showMenu
                },
            contentDescription = "",
        )

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(onClick = {
                didPressAddGroup()
                showMenu = false
            }) {
                Text("Add", color = MaterialTheme.colors.primary)
            }
            DropdownMenuItem(onClick = {
                didPressJoinGroup()
                showMenu = false
            }) {
                Text("Join", color = MaterialTheme.colors.primary)
            }
        }
    } }, body = body)
}