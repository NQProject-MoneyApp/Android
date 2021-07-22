package com.nqproject.MoneyApp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nqproject.MoneyApp.repository.Group


@Composable
fun GroupDetailsScreen(navController: NavController, group: Group) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Group details, id: $groupId",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary,)
    }
}