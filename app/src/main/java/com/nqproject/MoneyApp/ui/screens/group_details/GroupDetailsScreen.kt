package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen
import com.nqproject.MoneyApp.ui.screens.group_details.GroupUsersListComponent
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsHeader
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun GroupDetailsScreen(navController: NavController, group: Group) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<GroupDetailsViewModel>()
    val groupUsersList = viewModel.groupDetails.observeAsState(emptyList()).value
    val scrollState = rememberScrollState()

    GroupDetailsHeader(
        didPressBackButton = {
            Log.d(Config.MAIN_TAG, "didPressBackButton")
            navController.popBackStack()
        },
        didPressOptions = {
            Log.d(Config.MAIN_TAG, "didPressOptions")
        },
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painterResource(id = R.drawable.ic_burger),
                    modifier = Modifier
                        .size(132.dp)
                        .padding(8.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Total cost",
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                    Text(
                        text = "100$",
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Balance",
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                    Text(
                        text = "+ $50",
                        style = MaterialTheme.typography.h4,
                        color = Color.Green
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Settle up",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = {
                    Log.d(Config.MAIN_TAG, "new expense")
                },
                    modifier = Modifier.fillMaxWidth(),
                    shape = AbsoluteRoundedCornerShape(15)
                )
                { Text(
                    text = "New expense",
                    style = MaterialTheme.typography.h4
                )}
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.fetchGroupUsers(group.id)
                    }
                }) {
                    Text("Show group users")
                }
                Spacer(modifier = Modifier.height(20.dp))
                GroupUsersListComponent(navController, userBalanceList = groupUsersList, group, didPressAllExpenses = {
                    navController.navigate(MainNavigationScreen.ExpenseList.createRoute(group = it))
                })
            }
        },
        title = group.name
    )
}