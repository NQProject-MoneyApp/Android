package com.nqproject.MoneyApp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.ui.screens.add_group.AddGroupViewModel

@Composable
fun ProfileForm(
    name: String,
    email: String,
    onSaveChanges: (name: String, email: String) -> Unit
) {
    val viewModel = viewModel<AddGroupViewModel>()

    val userName = remember {
        ValidableValue(name,
            {
                when {
                    it.isEmpty() -> "Enter a user name"
                    else -> ""
                }
            }
        )
    }

    val userEmail = remember {
        ValidableValue(email,
            {
                when {
                    it.isEmpty() -> "Enter an email"
                    else -> ""
                }
            }
        )
    }

    val userNameValue = userName.value.observeAsState().value!!
    val userEmailValue = userEmail.value.observeAsState().value!!
    val addGroupLoading = viewModel.addGroupLoading.observeAsState(false).value

    InputField(
        focusRequester = FocusRequester(),
        fieldState = userName,
        focusRequesterAction = {},
        placeholder = "Name",
        keyboardType = KeyboardType.Text,
    )

    InputField(
        focusRequester = FocusRequester(),
        fieldState = userEmail,
        focusRequesterAction = {},
        placeholder = "Email",
        keyboardType = KeyboardType.Text,
    )

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Config.ROUNDED_CORNERS),
        enabled = !addGroupLoading,
        onClick = {
            userName.validate()
            userEmail.validate()
            if (!userName.isError() and !userEmail.isError())
                onSaveChanges(userNameValue, userEmailValue)
        }) {
        Text("Save", style = MaterialTheme.typography.h4)
    }
}

