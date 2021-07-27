package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen
import com.nqproject.MoneyApp.ui.screens.group_list.GroupsListViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun GroupUsersListComponent(userBalanceList: List<UserBalance>, group: Group, didPressAllExpenses: (Group) -> Unit) {

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Members",
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
            Spacer(modifier = Modifier.height(20.dp))
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