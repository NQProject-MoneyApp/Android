package com.nqproject.MoneyApp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.group_details.CodeAlertComponent
import com.nqproject.MoneyApp.ui.screens.group_details.GroupUsersListComponent
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsHeader
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsViewModel
import com.nqproject.MoneyApp.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun GroupDetailsScreen(
    onExpensesListNavigate: () -> Unit,
    onBackNavigate: () -> Unit,
    onAddExpenseNavigate: () -> Unit,
    onEditGroupNavigate: () -> Unit,
    onSuggestedPaymentNavigate: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<GroupDetailsViewModel>()
    val scrollState = rememberScrollState()
    var showCode by remember { mutableStateOf(false) }
    var code = ""
    val context = LocalContext.current
    val group = viewModel.group.observeAsState().value!!

    GroupDetailsHeader(
        didPressBackButton = {
            Log.d(StyleConfig.MAIN_TAG, "didPressBackButton")
            onBackNavigate()
        },
        didPressGenerateCode = {
            Log.d(StyleConfig.MAIN_TAG, "didPressGenerateCode")

            coroutineScope.launch {
                val result = viewModel.fetchGroupCode(group.id)

                when(result) {
                    is SimpleResult.Success -> {
                        code = result.data
                        showCode = true
                    }
                    is SimpleResult.Error -> {
                        Toast.makeText(context, "Error on fetch code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        },
        didPressEditGroup = {
            Log.d(StyleConfig.MAIN_TAG, "didPressEditGroup")
            onEditGroupNavigate()
        },
        body = {
            if (showCode) {
                CodeAlertComponent(onClose = {
                    showCode = false}, code = code)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(StyleConfig.MEDIUM_PADDING),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painterResource(id = group.icon.icon()),
                    modifier = Modifier
                        .size(StyleConfig.LARGE_ICON_SIZE)
                        .padding(StyleConfig.XSMALL_PADDING),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Spacer(modifier = Modifier.height(StyleConfig.LARGE_PADDING))
                Row(
                    modifier = Modifier
                        .padding(horizontal = StyleConfig.SMALL_PADDING)
                )
                {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Total cost",
                        style = MaterialTheme.typography.h4,
                        color = AppTheme.colors.primaryText
                    )
                    Text(
                        text = "\$${String.format(Locale.US, "%.2f", group.totalCost)}",
                        style = MaterialTheme.typography.h4,
                        color = AppTheme.colors.primaryText
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = StyleConfig.SMALL_PADDING)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Balance",
                        style = MaterialTheme.typography.h4,
                        color = AppTheme.colors.primaryText
                    )
                    Text(
                        text = "\$${String.format(Locale.US, "%.2f", group.userBalance)}",
                        style = MaterialTheme.typography.h4,
                        color = if (group.userBalance >= 0.0) Color.Green else Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))
                TextButton(onClick = {
                    onSuggestedPaymentNavigate()
                }) {
                    Text(
                        text = "Settle up",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(StyleConfig.XSMALL_PADDING))
                Button(onClick = {
                    Log.d(StyleConfig.MAIN_TAG, "new expense")
                    onAddExpenseNavigate()
                },
                    modifier = Modifier.fillMaxWidth(),
                    shape = AbsoluteRoundedCornerShape(StyleConfig.ROUNDED_CORNERS)
                )
                { Text(
                    text = "New expense",
                    style = MaterialTheme.typography.h4
                )}
                Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))
                GroupUsersListComponent(
                    userBalanceList = group.members,
                    group = group,
                    didPressAllExpenses = { onExpensesListNavigate() }
                )
            }
        },
        title = group.name
    )
}
