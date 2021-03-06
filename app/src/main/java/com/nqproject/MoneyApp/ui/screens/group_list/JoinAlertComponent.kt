package com.nqproject.MoneyApp.ui.screens.group_list
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.ui.AlertComponent
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun JoinAlertComponent(onClose: () -> Unit, onJoinPress: (code: String) -> Unit) {

    val codeState = remember { mutableStateOf("") }

    AlertComponent(onClose = { onClose() }) {
        Column(

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                "Join",
                color = AppTheme.colors.primaryText,
                style = MaterialTheme.typography.h4,
            )

            Spacer(modifier = Modifier.height(StyleConfig.XSMALL_PADDING))
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
                border = BorderStroke(StyleConfig.BORDER_STROKE, MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(StyleConfig.SMALL_PADDING)
                    .clickable {
                        //copy it
                    }
            ) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = codeState.value,
                    onValueChange = { newValue ->
                        codeState.value = newValue.uppercase()
                    },
                    label = { Text("Code", color = AppTheme.colors.hintText) },
                    shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = AppTheme.colors.primaryText,
                    )
                )
            }
            Spacer(modifier = Modifier.height(StyleConfig.XSMALL_PADDING))

            TextButton(onClick = { onJoinPress(codeState.value) }) {
                Text("Join",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4,
                )
            }
        }
    }
}