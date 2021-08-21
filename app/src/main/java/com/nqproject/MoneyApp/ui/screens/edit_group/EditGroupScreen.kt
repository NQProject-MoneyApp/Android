package com.nqproject.MoneyApp.ui.screens.edit_group

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.screens.add_group.AddGroupForm
import com.nqproject.MoneyApp.ui.screens.add_group.AddGroupHeader
import com.nqproject.MoneyApp.ui.screens.add_group.AddGroupViewModel
import com.nqproject.MoneyApp.ui.screens.add_group.ImageAlertComponent
import kotlinx.coroutines.launch

@Composable
fun EditGroupScreen(
    group: Group,
    onBackNavigate: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<AddGroupViewModel>()
    val loading = viewModel.loading.observeAsState(false).value

    val icons = viewModel.icons.observeAsState().value
    var showImageAlert by remember { mutableStateOf(false) }
    var chosenIcon by remember { mutableStateOf<MoneyAppIcon?>(MoneyAppIcon.from(group.icon))}

    AddGroupHeader(
        title = group.name,
        didPressBackButton = onBackNavigate,
        body = {
            Column(
                modifier = Modifier.fillMaxSize(),
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
                        defaultName = group.name,
                        defaultMembers = group.members,
                        icon = chosenIcon,
                        onAddImage = {
                            showImageAlert = true
                        },
                        onSave = { name: String, members: List<User> ->
                            coroutineScope.launch {
                                val result = viewModel.editGroup(
                                    group = group,
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
        }
    )
}