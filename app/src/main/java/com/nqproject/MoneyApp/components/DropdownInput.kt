package com.nqproject.MoneyApp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.ui.theme.AppColors
import com.nqproject.MoneyApp.ui.theme.AppTheme

@Composable
fun DropdownInput(
    label: String,
    selectedIndex: Int?,
    options: List<String>,
    onChanged: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val cardShape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS)

    Column {
        Card(
            backgroundColor = MaterialTheme.colors.secondary,
            shape = cardShape,
            modifier = Modifier
                .fillMaxWidth()
                .clip(cardShape)
                .clickable { expanded = !expanded }
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()

                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(StyleConfig.SMALL_PADDING)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = if (selectedIndex != null) options[selectedIndex] else label,
                    color = if (selectedIndex != null) AppTheme.colors.primaryText
                        else AppTheme.colors.hintText,
                    style = MaterialTheme.typography.h5
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .background(AppColors.DarkGray),
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(onClick = {
                    onChanged(index)
                    expanded = !expanded
                }) {
                    Text(
                        text = option,
                        color = AppTheme.colors.primaryText,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
    }

}

