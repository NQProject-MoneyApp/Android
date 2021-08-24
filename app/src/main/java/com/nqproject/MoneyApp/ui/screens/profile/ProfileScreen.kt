package com.nqproject.MoneyApp.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onBackNavigate: () -> Unit,
) {

    val viewModel = viewModel<ProfileViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val user = viewModel.user.observeAsState().value

    ProfileHeader(didPressBackButton = onBackNavigate) {
        if (user != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProfileForm(
                    name = user.name,
                    email = user.email,
                    onSaveChanges = { name: String, email: String ->
                        coroutineScope.launch {
                            val result = viewModel.editUserProfile(
                                pk = user.pk,
                                name = name,
                                email = email,
                            )
                            when (result) {
                                is SimpleResult.Error -> Toast
                                    .makeText(context, result.error, Toast.LENGTH_SHORT).show()
                                is SimpleResult.Success -> {
                                    SimpleResult.Success("Success")
                                    onBackNavigate()
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}