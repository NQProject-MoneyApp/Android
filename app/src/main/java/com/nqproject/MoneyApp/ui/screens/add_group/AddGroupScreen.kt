package com.nqproject.MoneyApp.ui.screens.add_group

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.repository.User
import kotlinx.coroutines.launch


@Composable
fun AddGroupScreen(
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val loading = viewModel.loading.observeAsState(false).value
    var showImageAlert by remember { mutableStateOf(false) }
    var chosenIcon by remember { mutableStateOf<MoneyAppIcon?>(null) }
    val icons = viewModel.icons.observeAsState().value

    AddGroupHeader(
        title = "New group",
        didPressBackButton = onBackNavigate,
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (showImageAlert) {
                    ImageAlertComponent(icons ?: MoneyAppIcon.values().toList(),
                        onIconChoose = {
                            showImageAlert = false
                            chosenIcon = it
                        },
                        onClose = {
                            showImageAlert = false

                        })
                }

                if (loading) {
                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {

                    AddGroupForm(
                        icon = chosenIcon,
                        onAddImage = {
                            showImageAlert = true
                        },
                        onSave = { name: String, members: List<User> ->
                            coroutineScope.launch {
                                val result = viewModel.addGroup(
                                    name = name,
                                    icon = chosenIcon ?: MoneyAppIcon.values().random(),
                                    members = members
                                )
                                when (result) {
                                    is SimpleResult.Error -> Toast
                                        .makeText(context, result.error, Toast.LENGTH_SHORT).show()
                                    is SimpleResult.Success -> {
                                        SimpleResult.Success("Success")
                                        onBackNavigate()
                                    }
                                }
                            }

                        })
                }
            }
        })
}