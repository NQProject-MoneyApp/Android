package com.nqproject.MoneyApp.ui.screens.suggested_payment

import SuggestedPaymentViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.StyleConfig

@Composable
fun SuggestedPaymentScreen(onBackNavigate: () -> Unit) {
    val viewModel = viewModel<SuggestedPaymentViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val suggestedPaymentsList = viewModel.suggestedPaymentsList.observeAsState(emptyList()).value
    val scrollState = rememberScrollState()
    val isLoading by viewModel.loading.observeAsState(false)

    SuggesterPaymentHeader(
        didPressBackButton = {
            onBackNavigate()
        },
        body = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(StyleConfig.MEDIUM_PADDING),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (isLoading) {
                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    when {
                        suggestedPaymentsList.isEmpty() -> {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(StyleConfig.XLARGE_PADDING)
                                    .padding(horizontal = StyleConfig.SMALL_PADDING),
                                shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
                                onClick = {
                                    // Todo
                                }) {
                                Text("New payment", style = MaterialTheme.typography.h4)
                            }
                            Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))
                            Text("You don't have any suggested payments",
                                style = MaterialTheme.typography.h4,
                                textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(StyleConfig.MEDIUM_PADDING))
                        }
                        else -> {
                            suggestedPaymentsList.forEach { payment ->
                                SuggestedPaymentComponent(
                                    payment = payment,
                                    onSave = {
                                        viewModel.savePayments(payment = payment)
                                    }
                                )
                                Spacer(modifier = Modifier.height(StyleConfig.SMALL_PADDING))
                            }
                        }
                    }
                }
            }
        }
    )
}

