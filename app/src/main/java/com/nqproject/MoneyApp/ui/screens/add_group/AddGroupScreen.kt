package com.nqproject.MoneyApp.ui.screens.add_group

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.AlertComponent
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun AddGroupScreen(
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
//    val loading = viewModel.loading.observeAsState(false).value
    val context = LocalContext.current
    var showImageAlert by remember { mutableStateOf(false) }
    var chosenIcon by remember { mutableStateOf<MoneyAppIcon?>(null) }
    var icons = emptyList<MoneyAppIcon>()

//    val choosenUsers = viewModel.choosenUsers.observeAsState(emptyList()).value
//    val friends = viewModel.userFriends.observeAsState(emptyList()).value
//    val fetchUserLoading = viewModel.fetchUserLoading.observeAsState(false).value


    var showUserAlert by remember { mutableStateOf(false) }

    AddGroupHeader(
        didPressBackButton = onBackNavigate,
        body = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,) {

            if (showImageAlert) {
                ImageAlertComponent(icons,
                    onIconChoose = {
                        showImageAlert = false
                        chosenIcon = it
                    },
                    onClose = {
                    showImageAlert = false

                })
            }

            if (showUserAlert) {

                AddMemberAlertComponent(onClose = { showUserAlert = false })
            }

            GroupNameForm(
                users = emptyList(),
                loading = false, // TODO
                icon = chosenIcon,
                onAddUser = {
                    showUserAlert = true
                },
                onAddImage = {
                    coroutineScope.launch {
                        val result = viewModel.icons()
                        when(result) {
                            is SimpleResult.Success -> {
                                icons = result.data
                                showImageAlert = true
                            }
                            is SimpleResult.Error -> {
                                Toast.makeText(context, "Error on fetch icons", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                },
                onSave = {

                if (it.isEmpty()) {
                    Toast.makeText(context, "Enter a group name", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(Config.MAIN_TAG, "on save group: $it")
                    coroutineScope.launch {
                        val result = viewModel.addGroup(name=it, icon = chosenIcon ?: MoneyAppIcon.values().random() )

                        when(result) {
                            is SimpleResult.Error -> Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                            is SimpleResult.Success -> {
                                SimpleResult.Success("Success")
                                onBackNavigate()
                            }
                        }
                    }
                }
            })
        }
    })
}

@Composable
private fun GroupNameForm(onSave: (name: String) -> Unit, icon: MoneyAppIcon?, loading: Boolean, onAddImage: () -> Unit, users: List<User>, onAddUser: () -> Unit) {
    val groupName = remember { mutableStateOf("") }

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier
            .width(132.dp)
            .height(132.dp),
        shape = RoundedCornerShape(20.dp),

        ) {

        if (icon != null) {
            Image(
                painterResource(id = icon.icon()),
                modifier = Modifier
                    .clickable { onAddImage() }
                    .padding(24.dp),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        } else {
            Image(
                painterResource(id = R.drawable.ic_add),
                modifier = Modifier
                    .clickable { onAddImage() }
                    .padding(50.dp),
                contentDescription = "",
            )
        }
    }

    InputField(groupName = groupName)

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
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Members", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            users.forEach {
                UserComponent(user = it) {
                    println("USER PRESS")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(33.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(10.dp),
//                enabled = !loading,
                onClick = {
                    onAddUser()
                }) {
                Text("Add", style = MaterialTheme.typography.h5)
            }
        }
    }

    Spacer(modifier = Modifier.height(21.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            onSave(groupName.value)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }

}

@Composable
private fun UserComponent(user: User, didPressComponent: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(horizontal = 24.dp),

        ) {
        Text(user.name, color=Color.White, modifier = Modifier.weight(1f))
        Image(
            painterResource(id = R.drawable.ic_cross),
            modifier = Modifier
                .clickable { didPressComponent() },
            contentDescription = "",
        )

    }
}

@Composable
private fun InputField(groupName: MutableState<String>) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = groupName.value,
        onValueChange = { newValue ->
            groupName.value = newValue
        },
        label = { Text("Group name", color = AppTheme.colors.hintText) },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
private fun AddMemberAlertComponent(onClose: () -> Unit) {

    val viewModel = viewModel<AddMemberViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value
    val context = LocalContext.current
    val users = viewModel.userFriends.observeAsState(emptyList()).value

//
//    var showImageAlert by remember { mutableStateOf(false) }
//    var chosenIcon by remember { mutableStateOf<MoneyAppIcon?>(null) }
//    var icons = emptyList<MoneyAppIcon>()
//    val friends = viewModel.userFriends.observeAsState(emptyList()).value
//    val fetchUserLoading = viewModel.fetchUserLoading.observeAsState(false).value

    Dialog(
        onDismissRequest = { onClose() },

    ) {

        Surface(
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.wrapContentSize()
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (loading) {
                    CircularProgressIndicator()
                } else {


                    users.forEach {
                        UserComponent(user = it) {
                            println("USER PRESS")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }

        }

    }

//    AlertComponent(onClose = { onClose() }) {
//
//
//    }
}