package com.nqproject.MoneyApp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.ui.screens.auth.login.LoginViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import com.nqproject.MoneyApp.ui.screens.auth.*
import com.nqproject.MoneyApp.ui.screens.auth.login.LoginResult

@Composable
fun LoginScreen(
    onRegisterNavigate: () -> Unit,
    onLoggedInNavigate: () -> Unit,
) {

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

        AuthHeader()

        LoginForm(loading = loading) { username, password ->
            coroutineScope.launch {
                val result = viewModel.login(username, password)

                when(result) {
                    is LoginResult.Success -> {
//                        navController.navigate(MainNavigationScreen.Groups.route) {
//                            popUpTo(MainNavigationScreen.LoginScreen.route) { inclusive = true }
//                        }
                        onLoggedInNavigate()
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
            onRegisterNavigate()
        }

    }
}

@Composable
private fun LoginForm(
    loading: Boolean,
    onLoginPressed: (username: String, password: String) -> Unit
) {

    val usernameState = remember {
        ValidableValue("",
            {
                when {
                    it.isEmpty() -> "Enter a username"
                    else -> ""
                }
            }
        )
    }
    val passwordState = remember {
        ValidableValue("",
            {
                when {
                    it.isEmpty() -> "Enter a username"
                    else -> ""
                }
            }
        )
    }

    val usernameValue = usernameState.value.observeAsState().value!!
    val passwordValue = passwordState.value.observeAsState().value!!

    AuthInputFields(
        usernameState = usernameState,
        passwordState = passwordState,
        onDone = {
            onLoginPressed(usernameValue, passwordValue)
        },
    )

    Spacer(modifier = Modifier.height(5.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            usernameState.validate()
            passwordState.validate()
            if (!usernameState.isError() and !passwordState.isError())
                onLoginPressed(usernameValue, passwordValue)
        }) {
        Text("Log in", style = MaterialTheme.typography.h4)
    }
}