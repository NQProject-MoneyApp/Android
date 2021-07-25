package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen
import com.nqproject.MoneyApp.ui.screens.auth.login.LoginResult
import com.nqproject.MoneyApp.ui.screens.group_details.CodeAlertComponent
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
    // TODO move to view model
    var showCode by remember { mutableStateOf(false) }
    var code = ""
    val context = LocalContext.current

    GroupDetailsHeader(
        didPressBackButton = {
            Log.d(Config.MAIN_TAG, "didPressBackButton")
            navController.popBackStack()
        },
        didPressGenerateCode = {
            Log.d(Config.MAIN_TAG, "didPressGenerateCode")

            coroutineScope.launch {
                val result = viewModel.fetchGroupCode(group.id)

                when(result) {
                    is SimpleResult.Success -> {
                        code = result.data
                        showCode = true
                    }
                    is SimpleResult.Error -> {
                        Toast.makeText(context, "Error on fetch code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        },
        body = {
            if (showCode) {
                CodeAlertComponent(onClose = {
                    showCode = false}, code = code)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(48.dp),
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
                        text = "-100$",
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
                    modifier = Modifier.fillMaxWidth()
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
                GroupUsersListComponent(navController, userBalanceList = groupUsersList, group, didPressAllExpenses = {
                    navController.navigate(MainNavigationScreen.ExpenseList.createRoute(group = it))
                })
            }
        },
        title = group.name
    )
}
