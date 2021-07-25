package com.nqproject.MoneyApp.ui.screens.expense_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.Group
import kotlinx.coroutines.launch

@Composable
fun ExpenseListScreen(navController: NavController, group: Group) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<ExpenseListViewModel>()
    val scrollState = rememberScrollState()
    val groupExpenses = viewModel.groupExpenses.observeAsState(emptyList()).value

    ExpenseListHeader(
        didPressBackButton = {
            Log.d(Config.MAIN_TAG, "didPressBackButton")
            navController.popBackStack()
        },
        didPressOptions = {
            Log.d(Config.MAIN_TAG, "didPressOptions")
        },
        body = {
             Column(
                 modifier = Modifier
                     .fillMaxSize()
                     .verticalScroll(scrollState)
                     .padding(48.dp),
                 verticalArrangement = Arrangement.Top,
                 horizontalAlignment = Alignment.CenterHorizontally,
             ) {
                 TextButton(onClick = {
                     coroutineScope.launch {
                         viewModel.fetchExpenses(group.id)
                     }
                 }) {
                     Text(
                         text = "Fetch expenses",
                         style = MaterialTheme.typography.h4,
                         color = MaterialTheme.colors.primary
                     )
                 }
                 Button(onClick = {
                     Log.d(Config.MAIN_TAG, "new expense")
                 })
                 { Text(
                     text = "New expense",
                     style = MaterialTheme.typography.h4
                 )
                 }
                 groupExpenses.forEach {
                     ExpenseListComponent(it)
                 }
             }
        },
        title = "Expenses")
}