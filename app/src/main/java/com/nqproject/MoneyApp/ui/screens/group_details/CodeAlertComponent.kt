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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.ui.AlertComponent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.draw.clip





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
                color = Color.White,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                shape = shape,
                border = BorderStroke(3.dp, MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape)
                    .clickable {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("label", code)
                        clipboard.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "Skopiowano!", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                Text(
                    code,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h4,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

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