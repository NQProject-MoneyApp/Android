package com.nqproject.MoneyApp.ui
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun AlertComponent(onClose: () -> Unit, body: @Composable () -> Unit) {

    AlertDialog(
        shape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS),
        onDismissRequest = { onClose() },
        text = {
            body()
        },
        confirmButton = {},
        // below line is use to add background color to our alert dialog
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = AppTheme.colors.primaryText,
        modifier = Modifier.animateContentSize().wrapContentSize()
    )
}

