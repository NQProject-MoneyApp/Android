package com.nqproject.MoneyApp.ui.screens.auth.registration

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.ui.screens.auth.AuthHeader
import com.nqproject.MoneyApp.ui.screens.auth.AuthInputFields
import com.nqproject.MoneyApp.ui.screens.auth.BottomOption
import com.nqproject.MoneyApp.ui.screens.auth.InputFieldValidator
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreen(
    onLoginNavigate: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<RegistrationViewModel>()
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

        RegistrationForm(loading = loading) { username, password, email ->
            coroutineScope.launch {
                Log.d(Config.MAIN_TAG, "On register $username $email $password")
                val result = viewModel.register(username, password, email)

                when(result) {
                    is RegistrationResult.Success -> {
                        onLoginNavigate()
                    }
                    is RegistrationResult.Failed -> {
                        Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(21.dp))
        BottomOption(text = "Already have account?", buttonText = "Login") {
            // TODO
            // block back button?
            // replace all screens?
//            navController.navigate(MainNavigationScreen.LoginScreen.route)
            onLoginNavigate()
        }
    }
}


@Composable
private fun RegistrationForm(
    loading: Boolean,
    onRegisterPressed: (username: String, password: String, email: String) -> Unit
) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    val usernameValidator by remember {
        mutableStateOf(InputFieldValidator<String> {
            when {
                it.isEmpty() -> "Enter a username"
                else -> ""
            }
        })
    }

    val emailValidator by remember {
        mutableStateOf(InputFieldValidator<String> {
            when {
                it.isEmpty() -> "Enter an email"
                else -> ""
            }
        })
    }

    val passwordValidator by remember {
        mutableStateOf(InputFieldValidator<String> {
            when {
                it.isEmpty() -> "Enter a password"
                else -> ""
            }
        })
    }

    AuthInputFields(
        usernameState = usernameState,
        passwordState = passwordState,
        emailState = emailState,
        onDone = {
            onRegisterPressed(usernameState.value, passwordState.value, emailState.value)
        },
        usernameValidator = usernameValidator,
        emailValidator = emailValidator,
        passwordValidator = passwordValidator,
    )

    Spacer(modifier = Modifier.height(21.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            usernameValidator.validate(usernameState.value)
            passwordValidator.validate(passwordState.value)
            emailValidator.validate(emailState.value)
            if (!usernameValidator.isError() and !passwordValidator.isError() and !emailValidator.isError())
                onRegisterPressed(usernameState.value, passwordState.value, emailState.value)
        }) {
        Text("Register", style = MaterialTheme.typography.h4)
    }
}