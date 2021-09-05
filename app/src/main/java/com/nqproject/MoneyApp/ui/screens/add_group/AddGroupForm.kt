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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.components.ChooseUsersComponent
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.repository.User

@Composable
fun AddGroupForm(
    defaultName: String = "",
    defaultMembers: List<User>? = null,
    icon: MoneyAppIcon?,
    onAddImage: () -> Unit,
    onSave: (name: String, members: List<User>) -> Unit) {

    val viewModel = viewModel<AddGroupViewModel>()
    val groupMembers = viewModel.userFriends.observeAsState().value!!

    val groupName = remember {
        ValidableValue(defaultName,
            {
                when {
                    it.isEmpty() -> "Enter a group name"
                    else -> ""
                }
            }
        )
    }

    val newGroupMembers = remember {
        ValidableValue(defaultMembers ?: groupMembers,
            {
                when {
                    it.isEmpty() -> "Choose group members"
                    else -> ""
                }
            }
        )
    }

    val friends = viewModel.userFriends.observeAsState(emptyList()).value
    val groupNameValue = groupName.value.observeAsState().value!!
    val groupMembersValue = newGroupMembers.value.observeAsState().value!!
    val addGroupLoading = viewModel.addGroupLoading.observeAsState(false).value

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier
            .size(Config.LARGE_ICON_SIZE),
        shape = RoundedCornerShape(Config.ROUNDED_CORNERS),

        ) {

        if (icon != null) {
            Image(
                painterResource(id = icon.icon()),
                modifier = Modifier
                    .clickable { onAddImage() }
                    .padding(Config.MEDIUM_PADDING),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        } else {
            Image(
                painterResource(id = R.drawable.ic_add),
                modifier = Modifier
                    .clickable { onAddImage() }
                    .padding(Config.XLARGE_PADDING),
                contentDescription = "",
            )
        }
    }

    Spacer(modifier = Modifier.height(Config.SMALL_PADDING))

    InputField(
        focusRequester = FocusRequester(),
        fieldState = groupName,
        focusRequesterAction = {},
        placeholder = "Group name",
        keyboardType = KeyboardType.Text,
    )

    if (friends.isNotEmpty()) {
        ChooseUsersComponent(
            title = "Members",
            groupMembers = friends,
            chosenMembers = newGroupMembers,
        )
    }

    Spacer(modifier = Modifier.height(Config.MEDIUM_PADDING))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(Config.XLARGE_PADDING)
            .padding(horizontal = Config.SMALL_PADDING),
        shape = RoundedCornerShape(Config.ROUNDED_CORNERS),
        enabled = !addGroupLoading,
        onClick = {
            groupName.validate()
            if(!groupName.isError())
                onSave(groupNameValue, groupMembersValue)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }
}