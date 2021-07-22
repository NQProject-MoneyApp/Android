package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsHeader

@Composable
fun GroupDetailsScreen(navController: NavController, group: Group) {

    val scrollState = rememberScrollState()
    
    GroupDetailsHeader(
        didPressBackArrow = {
            Log.d(Config.MAIN_TAG, "didPressBackArrow")
        },
        didPressOptions = {
            Log.d(Config.MAIN_TAG, "didPressOptions")
        },
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painterResource(id = R.drawable.ic_burger),
                    modifier = Modifier
                        .size(132.dp)
                        .padding(8.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
//                Row()
//                {
//                    Text(
//                    )
//                }
                Text(
                    text = "Group details, id: ${group.id}",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primary,)
            }
        },
        title = "Group ${group.id}")
}