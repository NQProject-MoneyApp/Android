package com.nqproject.MoneyApp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.navigation.MainNavigationScreen
import com.nqproject.MoneyApp.ui.screens.login.LoginViewModel
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import com.nqproject.MoneyApp.ui.screens.login.LoginResult


@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<LoginViewModel>()

    val loading = viewModel.loading.observeAsState(false).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 34.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Header()

        LoginForm(loading = loading) { username, password ->
            coroutineScope.launch {
                val result = viewModel.login(username, password)

                when(result) {
                    is LoginResult.Success -> {
                        navController.navigate(MainNavigationScreen.Groups.route)
                    }
                    is LoginResult.Failed -> {
                        Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(21.dp))
        BottomOption(text = "Forgot password?", buttonText = "Reset") {
            // Action
        }
        BottomOption(text = "No account yet?", buttonText = "Register") {
            // Action
        }

    }
}

@Composable
private fun Header() {
    Spacer(modifier = Modifier.height(39.dp))
    Image(
        painterResource(id = R.drawable.ic_icon),
        modifier = Modifier.size(132.dp),
        contentDescription = ""
    )
    Spacer(modifier = Modifier.height(69.dp))

    Text(
        text = "MoneyApp",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.primary,)
    Spacer(modifier = Modifier.height(40.dp))
}


@Composable
private fun BottomOption(text: String, buttonText: String, onClick: () -> Unit) {
    Row(

        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.width(4.dp))
        TextButton(
            modifier = Modifier.height(39.dp),
            onClick = onClick
        ) {
            Text(buttonText, style = MaterialTheme.typography.h5, color = AppTheme.colors.primary)
        }

    }
}


@Composable
private fun LoginForm(
    loading: Boolean,
    onLoginPressed: (username: String, password: String) -> Unit
) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    InputFields(
        usernameState = usernameState,
        passwordState = passwordState,
    )

    Spacer(modifier = Modifier.height(21.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            onLoginPressed(usernameState.value, passwordState.value)
        }) {
        Text("Login", style = MaterialTheme.typography.h4)
    }

}

@Composable
private fun InputFields(
    usernameState: MutableState<String>,
    passwordState: MutableState<String>,
) {

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