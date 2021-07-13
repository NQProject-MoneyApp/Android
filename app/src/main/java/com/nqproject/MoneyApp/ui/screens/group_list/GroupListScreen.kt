package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen
import com.nqproject.MoneyApp.ui.screens.group_list.GroupListComponent
import com.nqproject.MoneyApp.ui.screens.group_list.GroupListHeader
import com.nqproject.MoneyApp.ui.screens.group_list.GroupsListViewModel
import kotlinx.coroutines.launch

@Composable
fun GroupListScreen(navController: NavController) {

    val viewModel = viewModel<GroupsListViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var groupsList = viewModel.groupsList.observeAsState(emptyList()).value
    val loading = viewModel.loading.observeAsState(false).value
    val scrollState = rememberScrollState()

    GroupListHeader(didPressUserButton = {
        Log.d(Config.MAIN_TAG, "didPressUserButton")

    }, didPressAddGroup = {
        Log.d(Config.MAIN_TAG, "didPressAddGroup")
        navController.navigate(MainNavigationScreen.AddGroups.route)

    }, body = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row() {
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.fetchGroups()
                    }
                }) { Text("Fetch groups") }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = {
                    AuthenticationManager.token = null
                    // TODO
                    // block back button?
                    // replace all screens?
                    navController.navigate(MainNavigationScreen.LoginScreen.route)
                }) { Text("Logout") }
            }
            
            if (loading) {
                Spacer(modifier = Modifier.height(60.dp))
                CircularProgressIndicator()
            } else {
                Spacer(modifier = Modifier.height(60.dp))
                groupsList.forEach {
                    GroupListComponent(it)
                }
            }
        }
    })
}

