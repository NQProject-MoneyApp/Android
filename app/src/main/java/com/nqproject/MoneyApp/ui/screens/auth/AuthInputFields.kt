package com.nqproject.MoneyApp.ui.screens.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.components.InputField
import com.nqproject.MoneyApp.components.ValidableValue

@Composable
fun AuthInputFields(
    usernameState: ValidableValue<String>,
    passwordState: ValidableValue<String>,
    onDone: () -> Unit,
    emailState: ValidableValue<String>? = null,
) {

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
        },
    )

    Spacer(modifier = Modifier.height(5.dp))

    if (emailState != null) {
        InputField(
            focusRequester = emailFocusRequester,
            fieldState = emailState,
            keyboardType = KeyboardType.Email,
            placeholder = "Email",
            focusRequesterAction = {
                passwordFocusRequester.requestFocus()
            },
        )
        Spacer(modifier = Modifier.height(5.dp))
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
