package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.repository.User

@Composable
fun GroupUsersListComponent(userBalanceList: List<UserBalance>) {

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            userBalanceList.forEach {
                GroupBalanceComponent(it)
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text("All expenses",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}