package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.ui.screens.auth.InputField

@Composable
fun AddGroupForm(onSave: (name: String) -> Unit, icon: MoneyAppIcon?, onAddImage: () -> Unit) {
    val groupName = remember { mutableStateOf("") }
    val viewModel = viewModel<AddGroupViewModel>()

    val chosenUsers = viewModel.chosenUsers.observeAsState(emptyList()).value
    val friends = viewModel.userFriends.observeAsState(emptyList()).value
    val addGroupLoading = viewModel.addGroupLoading.observeAsState(false).value

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

    Spacer(modifier = Modifier.height(16.dp))

    InputField(
        focusRequester = FocusRequester(),
        fieldState = groupName,
        focusRequesterAction = {},
        placeholder = "Group name",
        keyboardType = KeyboardType.Text,
    )

    if (friends.isNotEmpty()) {
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


                friends.forEach {
                    AddUserComponent(
                        user = it,
                        check=chosenUsers.contains(it),
                        didPressComponent = {
                            if (chosenUsers.contains(it)) {
                                viewModel.removeChosenMember(it)
                            } else {
                                viewModel.addChosenMember(it)
                            }
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
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
        enabled = !addGroupLoading,
        onClick = {
            onSave(groupName.value)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }
}