package com.nqproject.MoneyApp.ui.screens.group_details

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.nqproject.MoneyApp.ui.AlertComponent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.draw.clip
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.ui.theme.AppTheme


@Composable
fun CodeAlertComponent(onClose: () -> Unit, code: String) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(25)
    AlertComponent(onClose = { onClose() }) {
        Column(

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                "Share the code with friends!",
                color = AppTheme.colors.primaryText,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(Config.XSMALL_PADDING),
            )

            Spacer(modifier = Modifier.height(Config.XSMALL_PADDING))
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = shape,
                border = BorderStroke(Config.BORDER_STROKE, MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Config.SMALL_PADDING)
                    .clip(shape)
                    .clickable {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("group code", code)
                        clipboard.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "Copied", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                Text(
                    code,
                    color = AppTheme.colors.primaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(Config.SMALL_PADDING),
                    style = MaterialTheme.typography.h4,
                )
            }
            Spacer(modifier = Modifier.height(Config.XSMALL_PADDING))
            TextButton(onClick = { onClose() }) {
                Text(
                    "Back",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h4,
                )
            }
        }
    }
}