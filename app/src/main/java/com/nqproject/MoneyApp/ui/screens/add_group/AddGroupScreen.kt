package com.nqproject.MoneyApp.ui.screens.add_group

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun AddGroupScreen(
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val loading = viewModel.loading.observeAsState(false).value
    var showImageAlert by remember { mutableStateOf(false) }
    val chosenUsers = viewModel.chosenUsers.observeAsState().value
    var chosenIcon by remember { mutableStateOf<MoneyAppIcon?>(null) }
    val icons = viewModel.icons.observeAsState().value

    AddGroupHeader(
        didPressBackButton = onBackNavigate,
        body = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,) {

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
                    onSave = {

                        if (it.isEmpty()) {
                            Toast.makeText(context, "Enter a group name", Toast.LENGTH_SHORT).show()
                        } else {
                            coroutineScope.launch {
                                val result = viewModel.addGroup(name=it, icon = chosenIcon ?: MoneyAppIcon.values().random(), chosenUsers ?: emptyList())

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
        }
    })
}