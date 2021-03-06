package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.ui.AlertComponent

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
                        .size(StyleConfig.MEDIUM_ICON_SIZE)
                        .padding(StyleConfig.XSMALL_PADDING)
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