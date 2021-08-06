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
import com.nqproject.MoneyApp.components.ValidableValue
import com.nqproject.MoneyApp.ui.screens.auth.*
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

                when (result) {
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
    val emailState = remember {
        ValidableValue("",
            {
                when {
                    it.isEmpty() -> "Enter an email"
                    else -> ""
                }
            }
        )
    }

    val usernameValue = usernameState.value.observeAsState().value!!
    val passwordValue = passwordState.value.observeAsState().value!!
    val emailValue = passwordState.value.observeAsState().value!!

    AuthInputFields(
        usernameState = usernameState,
        passwordState = passwordState,
        emailState = emailState,
        onDone = {
            onRegisterPressed(
                usernameValue, passwordValue, emailValue
            )
        },
    )

    Spacer(modifier = Modifier.height(21.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = !loading,
        onClick = {
            usernameState.validate()
            passwordState.validate()
            emailState.validate()
            if (!usernameState.isError() and !passwordState.isError() and !emailState.isError())
                onRegisterPressed(
                    usernameValue, passwordValue,
                    emailValue
                )
        }) {
        Text("Register", style = MaterialTheme.typography.h4)
    }
}