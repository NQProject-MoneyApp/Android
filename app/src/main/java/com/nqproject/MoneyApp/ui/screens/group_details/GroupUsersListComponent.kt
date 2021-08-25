package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.User

@Composable
fun GroupUsersListComponent(userBalanceList: List<User>, group: Group, didPressAllExpenses: (Group) -> Unit) {

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(Config.ROUNDED_CORNERS),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(Config.LARGE_PADDING)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Member",
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "Balance",
                    color = MaterialTheme.colors.primary
                )
            }
            userBalanceList.forEach {
                GroupBalanceComponent(it)
            }
            Spacer(modifier = Modifier.height(Config.MEDIUM_PADDING))
            TextButton(onClick = {
                didPressAllExpenses(group)
            }) {
                Text("All expenses",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}