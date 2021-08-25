package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.group_details.CodeAlertComponent
import com.nqproject.MoneyApp.ui.screens.group_details.GroupUsersListComponent
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsHeader
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsViewModel
import com.nqproject.MoneyApp.ui.screens.no_internet.NoInternetViewModel
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.util.*
import java.net.UnknownHostException

import java.net.InetAddress




@Composable
fun NoInternetScreen(
    onBackNavigate: () -> Unit
) {
    val viewModel = viewModel<NoInternetViewModel>()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painterResource(id = R.drawable.ic_wifi_slash_solid),
            contentDescription = "",
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("WHOOPS!",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        Text("SLOW OR NO INTERNET CONNECTION",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Check your internet settings and try again",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(49.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                if(viewModel.checkInternetConnection(context)) {
                    onBackNavigate()
                }
            }) {
            Text("TRY AGAIN", style = MaterialTheme.typography.h4)
        }
    }
}
