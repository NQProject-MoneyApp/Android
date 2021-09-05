package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.R

@Composable
fun GroupDetailsHeader(
    didPressBackButton: () -> Unit,
    didPressGenerateCode: () -> Unit,
    didPressEditGroup: () -> Unit,
    body: @Composable () -> Unit,
    title: String) {

    var showMenu by remember { mutableStateOf(false) }

    Column() {
        TopAppBar(
            title = { Text(text = title, style = MaterialTheme.typography.h4) },
            backgroundColor = MaterialTheme.colors.background,
            navigationIcon = {
                IconButton(onClick = { didPressBackButton() }) {
                    Image(
                        painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "",
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { showMenu = true }) {
                    Image(
                        painterResource(id = R.drawable.ic_menu),
                        contentDescription = "",
                    )

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            didPressGenerateCode()
                            showMenu = false
                        }) {
                            Text("Code", color = MaterialTheme.colors.primary)
                        }
                        DropdownMenuItem(onClick = {
                            didPressEditGroup()
                            showMenu = false
                        }) {
                            Text("Edit group", color = MaterialTheme.colors.primary)
                        }
                    }
                }
            }
        )
        body()
    }
}