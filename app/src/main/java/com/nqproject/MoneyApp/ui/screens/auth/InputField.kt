package com.nqproject.MoneyApp.ui.screens.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.ui.theme.AppTheme
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip


@Composable
fun InputField(focusRequester: FocusRequester, fieldState: MutableState<String>, focusRequesterAction: () -> Unit, placeholder: String, keyboardType: KeyboardType, validator: ((content: String) -> String)? = null) {
    val fieldShape = RoundedCornerShape(10.dp)
    var invalidInputMessage by remember { mutableStateOf("") }

    val visualTransformation = if (keyboardType == KeyboardType.Password) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    TextField(
        isError = invalidInputMessage.isNotEmpty(),
        modifier = Modifier
            .clip(shape = fieldShape)
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_TAB ||
                    it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_ENTER
                ) {
                    focusRequesterAction()
                    true //true -> consumed
                } else false
            },
        value = fieldState.value,
        onValueChange = { newValue ->
            fieldState.value = newValue.filter { it != '\n' && it != '\t' }
            invalidInputMessage = validator?.let { it(fieldState.value) } ?: ""
        },
        label = { Text(placeholder, color = AppTheme.colors.hintText) },
        shape = fieldShape,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusRequesterAction()
        }),
    )
    Text(
        text = invalidInputMessage,
        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
    )
}
