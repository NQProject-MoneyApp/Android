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
import androidx.compose.ui.unit.dp
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
                color = Color.White,
                style = MaterialTheme.typography.h4,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(25),
                border = BorderStroke(3.dp, MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = Color.White,
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { onJoinPress(codeState.value) }) {
                Text("Join",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4,
                )
            }
        }
    }
}