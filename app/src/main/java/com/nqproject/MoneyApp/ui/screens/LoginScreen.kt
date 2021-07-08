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
import androidx.navigation.NavHostController
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen


@Composable
fun LoginScreen(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painterResource(id = R.drawable.ic_icon),
            modifier = Modifier.size(100.dp),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "MoneyApp",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary,)
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = {
            navController.navigate(MainNavigationScreen.Groups.route)
        }) {
            Text("Login")
        }
    }

}