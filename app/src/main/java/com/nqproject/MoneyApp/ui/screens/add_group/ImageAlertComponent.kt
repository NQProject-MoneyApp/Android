package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.ui.AlertComponent
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun ImageAlertComponent(icons: List<MoneyAppIcon>, onClose: () -> Unit, onIconChoose: (MoneyAppIcon) -> Unit) {

    val scrollState = rememberScrollState()

    AlertComponent(onClose = { onClose() }) {
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            icons.forEach {
                Image(
                    painterResource(id = it.icon()),
                    modifier = Modifier
                        .size(Config.MEDIUM_ICON_SIZE)
                        .padding(Config.XSMALL_PADDING)
                        .clickable{
                            onIconChoose(it)
                        },
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }
        }
    }
}