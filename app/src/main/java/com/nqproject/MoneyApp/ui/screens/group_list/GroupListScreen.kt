package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.ui.screens.group_list.GroupListComponent
import com.nqproject.MoneyApp.ui.screens.group_list.GroupListHeader
import com.nqproject.MoneyApp.ui.screens.group_list.GroupsListViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.group_list.JoinAlertComponent

@Composable
fun GroupListScreen(
    onAddGroupNavigate: () -> Unit,
    onGroupDetailsNavigate: (group: Group) -> Unit,
    onLoginNavigate: () -> Unit,
) {

    val viewModel = viewModel<GroupsListViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val groupsList = viewModel.groupsList.observeAsState(emptyList()).value
    val loading = viewModel.loading.observeAsState(false).value
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    // TODO move to view model?
    var showJoinAlert by remember { mutableStateOf(false) }

    GroupListHeader(
        onLogout = {
            AuthenticationManager.token = null
            onLoginNavigate()
        },
        didPressUserButton = {
            Log.d(Config.MAIN_TAG, "didPressUserButton")

        },
        didPressAddGroup = {
            Log.d(Config.MAIN_TAG, "didPressAddGroup")
            onAddGroupNavigate()

        },
        didPressJoinGroup = {
            Log.d(Config.MAIN_TAG, "didPressJoinGroup")
            showJoinAlert = true

        },
        body = {

        if (showJoinAlert) {
            JoinAlertComponent(onClose = { showJoinAlert = false }) {
                Log.d(Config.MAIN_TAG, "tryJoinToGroup")
                showJoinAlert = false
                coroutineScope.launch {
                    val result = viewModel.join(it)
                    when(result) {
                        is SimpleResult.Success -> {
                            viewModel.updateGroups()
                        }
                        is SimpleResult.Error -> {
                            Log.d(Config.MAIN_TAG, "TOAST")
                            Toast.makeText(context, "Error on join to group", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            
            if (loading) {
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator()
            } else {
                groupsList.forEach {
                    GroupListComponent(it,
                    didPressComponent = {
                        onGroupDetailsNavigate(it)
                    })
                    Spacer(modifier = Modifier.height(21.dp))
                }
            }
        }
    })
}

