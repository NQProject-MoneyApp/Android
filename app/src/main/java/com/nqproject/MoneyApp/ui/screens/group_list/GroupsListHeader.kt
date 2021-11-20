package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.R

@Composable
fun GroupListHeader(
    didPressUserButton: () -> Unit,
    didPressAddGroup: () -> Unit,
    didPressJoinGroup: () -> Unit,
    onLogout: () -> Unit,
    didPressAboutButton: () -> Unit,
    body: @Composable () -> Unit) {

    var showLeftMenu by remember { mutableStateOf(false) }
    var showRightMenu by remember { mutableStateOf(false) }

    Column() {
        TopAppBar(
            title = { Text(text = "Groups", style = MaterialTheme.typography.h4) },
            backgroundColor = MaterialTheme.colors.background,
            navigationIcon = {
                IconButton(onClick = { showLeftMenu = true }) {
                    Image(
                        painterResource(id = R.drawable.ic_user),
                        contentDescription = "",
                        modifier = Modifier.padding(StyleConfig.XSMALL_PADDING)
                    )
                }

                DropdownMenu(
                    expanded = showLeftMenu,
                    onDismissRequest = { showLeftMenu = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            didPressAboutButton()
                            showLeftMenu = false
                        }
                    ) {
                        Text("About", color = MaterialTheme.colors.primary)
                    }
                    DropdownMenuItem(
                        onClick = {
                            didPressUserButton()
                            showLeftMenu = false
                        }
                    ) {
                            Text("Profile", color = MaterialTheme.colors.primary)
                    }
                    DropdownMenuItem(
                        onClick = {
                            onLogout()
                            showLeftMenu = false
                        }
                    ) {
                        Text("Log out", color = MaterialTheme.colors.primary)
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = { showRightMenu = true }) {
                    Image(
                        painterResource(id = R.drawable.ic_add),
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
                }
            }
        )
        body()
    }
}