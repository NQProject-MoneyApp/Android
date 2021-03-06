package com.nqproject.MoneyApp.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.components.BasicHeader
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.theme.AppTheme
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

    BasicHeader(title = "Profile", didPressBackButton = onBackNavigate) {
        if (user != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(StyleConfig.SMALL_PADDING),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape,
                    border = BorderStroke(StyleConfig.BORDER_STROKE, MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(160.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = StyleConfig.SMALL_PADDING)

                    ) {
                        Text(
                            user.name.substring(0, 2).uppercase(),
                            color = AppTheme.colors.hintText,
                            style = MaterialTheme.typography.h3
                        )
                    }
                }

                Spacer(modifier = Modifier.height(StyleConfig.SMALL_PADDING))

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