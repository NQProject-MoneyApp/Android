package com.nqproject.MoneyApp.ui.screens.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun AuthInputFields(usernameState: MutableState<String>, passwordState: MutableState<String>, emailState: MutableState<String>? = null,) {

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = usernameState.value,
        onValueChange = { newValue ->
            usernameState.value = newValue
        },
        label = { Text("Username", color = AppTheme.colors.hintText) },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )

    if (emailState != null) {
        Spacer(modifier = Modifier.height(21.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = emailState.value,
            onValueChange = { newValue ->
                emailState.value = newValue
            },
            label = { Text("Email", color = AppTheme.colors.hintText) },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

    Spacer(modifier = Modifier.height(21.dp))

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = passwordState.value,
        onValueChange = { newValue ->
            passwordState.value = newValue
        },
        label = { Text("Password", color = AppTheme.colors.hintText) },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
    )
}