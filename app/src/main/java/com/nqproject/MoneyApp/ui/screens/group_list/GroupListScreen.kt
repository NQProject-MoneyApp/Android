package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.group_list.JoinAlertComponent

@Composable
fun GroupListScreen(
    onAddGroupNavigate: () -> Unit,
    onGroupDetailsNavigate: (group: Group) -> Unit,
    onLoginNavigate: () -> Unit,
    onUserProfileNavigate: () -> Unit,
) {
    val viewModel = viewModel<GroupsListViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val groupsList = viewModel.groupsList.observeAsState(emptyList()).value
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var showJoinAlert by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.loading.observeAsState(false)

    GroupListHeader(
        onLogout = {
            AuthenticationManager.token = null
            onLoginNavigate()
        },
        didPressUserButton = {
            Log.d(Config.MAIN_TAG, "didPressUserButton")
            onUserProfileNavigate()
        },
        didPressAddGroup = {
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
                    if (result is SimpleResult.Error) {
                        Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                viewModel.updateGroups()
                        },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.background,
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (groupsList.isNotEmpty()) {
                    groupsList.forEach {
                        GroupListComponent(it,
                            didPressComponent = {
                                onGroupDetailsNavigate(it)
                            })
                        Spacer(modifier = Modifier.height(21.dp))
                    }
                } else {
                    Text("You don't have any groups", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(21.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(49.dp)
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            onAddGroupNavigate()
                        }) {
                        Text("Add", style = MaterialTheme.typography.h4)
                    }
                }
            }
        }
    })
}

