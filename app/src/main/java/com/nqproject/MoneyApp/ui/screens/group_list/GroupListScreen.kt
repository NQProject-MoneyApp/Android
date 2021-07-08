package com.nqproject.MoneyApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen


@Composable
fun GroupListScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Group list",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary,)
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = {
            navController.navigate(MainNavigationScreen.GroupDetails.createRoute(1))
        }) {
            Text("Group 1")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(MainNavigationScreen.GroupDetails.createRoute(2))
        }) {
            Text("Group 2")
        }
    }

}