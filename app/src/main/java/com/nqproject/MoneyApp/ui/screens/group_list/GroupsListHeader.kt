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
fun GroupListHeader(didPressUserButton: () -> Unit, didPressAddGroup: () -> Unit, didPressJoinGroup: () -> Unit, onLogout: () -> Unit, body: @Composable () -> Unit) {

    var showLeftMenu by remember { mutableStateOf(false) }
    var showRightMenu by remember { mutableStateOf(false) }

    Header(title = "Groups",
        leftIcon = {
            Row() {

                Image(
                    painterResource(id = R.drawable.ic_user),
                    modifier = Modifier
                        .clickable { showLeftMenu = true },
                    contentDescription = "",
                )

                DropdownMenu(
                    expanded = showLeftMenu,
                    onDismissRequest = { showLeftMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        onLogout()
                        showLeftMenu = false
                    }) {
                        Text("Logout", color = MaterialTheme.colors.primary)
                    }
                }
            }

    }, rightIcon = {

    Row() {

        Image(
            painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .clickable { showRightMenu = !showRightMenu },
            contentDescription = "",
        )

        DropdownMenu(
            expanded = showRightMenu,
            onDismissRequest = { showRightMenu = false }
        ) {
            DropdownMenuItem(onClick = {
                didPressAddGroup()
                showRightMenu = false
            }) {
                Text("Add", color = MaterialTheme.colors.primary)
            }
            DropdownMenuItem(onClick = {
                didPressJoinGroup()
                showRightMenu = false
            }) {
                Text("Join", color = MaterialTheme.colors.primary)
            }
        }
    } }, body = body)
}