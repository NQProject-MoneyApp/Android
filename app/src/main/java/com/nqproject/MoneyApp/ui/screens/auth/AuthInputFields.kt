package com.nqproject.MoneyApp.ui.screens.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AuthInputFields(usernameState: MutableState<String>, passwordState: MutableState<String>, onDone: () -> Unit, emailState: MutableState<String>? = null) {

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    InputField(
        fieldState = usernameState,
        focusRequester = FocusRequester(),
        keyboardType = KeyboardType.Text,
        placeholder = "Username",
        focusRequesterAction = {
            emailState?.let {
                emailFocusRequester.requestFocus()
            } ?: passwordFocusRequester.requestFocus()
        })

    Spacer(modifier = Modifier.height(21.dp))

    if (emailState != null) {
        InputField(
            focusRequester = emailFocusRequester,
            fieldState = emailState,
            keyboardType = KeyboardType.Email,
            placeholder = "Email",
            focusRequesterAction = {
                passwordFocusRequester.requestFocus()
            })
        Spacer(modifier = Modifier.height(21.dp))
    }

    InputField(
        focusRequester = passwordFocusRequester,
        fieldState = passwordState,
        keyboardType = KeyboardType.Password,
        placeholder = "Password",
        focusRequesterAction = {
            onDone()
            focusManager.clearFocus()
        },
    )
}
