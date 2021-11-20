package com.nqproject.MoneyApp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object StyleConfig {
    val MAIN_TAG = "MONEY_APP"
    val BORDER_STROKE = 3.dp
    val XSMALL_PADDING = 8.dp
    val SMALL_PADDING = 16.dp
    val MEDIUM_PADDING = 24.dp
    val LARGE_PADDING = 32.dp
    val XLARGE_PADDING = 48.dp
    val XXLARGE_PADDING = 96.dp
    val MEDIUM_ICON_SIZE = 80.dp
    val LARGE_ICON_SIZE = 132.dp
    val ROUNDED_CORNERS = 15.dp

    val CARD_SHAPE
        @Composable
        get() = RoundedCornerShape(ROUNDED_CORNERS)
}