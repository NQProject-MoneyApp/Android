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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch


@Composable
fun AddGroupScreen(navController: NavController) {

    val viewModel = viewModel<AddGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val loading = viewModel.loading.observeAsState(false).value
    val context = LocalContext.current

    Column() {
        Spacer(modifier = Modifier.height(20.dp))

        AddGroupHeader(didPressBackButton = {
            navController.popBackStack()

        }, didPressMenuButton = {
            Log.d("MONEY_APP", "didPressMenuButton")

        }, body = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,) {
                GroupNameForm(loading=loading, onSave = {

                    if (it.isEmpty()) {
                        Toast.makeText(context, "Enter a group name", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("MONEY_APP", "on save group: $it")
                        coroutineScope.launch {
                            val result = viewModel.addGroup(name=it)

                            when(result) {
                                is SimpleResult.Error -> Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                                is SimpleResult.Success -> {
                                    SimpleResult.Success("Success")
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                })
            }
        })
    }
}

@Composable
private fun GroupNameForm(onSave: (name: String) -> Unit, loading: Boolean) {
    val groupName = remember { mutableStateOf("") }

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier.width(132.dp).height(132.dp),
        shape = RoundedCornerShape(20.dp),

        ) {
        Image(
            painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .clickable { Log.d("MONEY_APP", "Did press add group image") }
                .padding(50.dp),
            contentDescription = "",
        )
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
