package com.nqproject.MoneyApp.ui.screens.add_group

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun AddGroupScreen(
    onBackNavigate: () -> Unit
) {

    val viewModel = viewModel<AddGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value
    val context = LocalContext.current
    var showImageAlert by remember { mutableStateOf(false) }
    var chosenIcon by remember { mutableStateOf<MoneyAppIcon?>(null) }
    var icons = emptyList<MoneyAppIcon>()


    AddGroupHeader(
        didPressBackButton = onBackNavigate,
        didPressMenuButton = {
        Log.d(Config.MAIN_TAG, "didPressMenuButton")

    }, body = {
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
            GroupNameForm(
                loading=loading,
                icon=chosenIcon,
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
private fun GroupNameForm(onSave: (name: String) -> Unit, icon: MoneyAppIcon?, loading: Boolean, onAddImage: () -> Unit) {
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
