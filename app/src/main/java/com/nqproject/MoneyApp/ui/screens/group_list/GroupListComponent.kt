package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun GroupListComponent(group: Group, didPressComponent: (Group) -> Unit) {
    val icon = if (group.isFavourite) R.drawable.ic_star_select else R.drawable.ic_star
    val dateFormat = SimpleDateFormat("dd-MM-yyy")
    val cardShape = RoundedCornerShape(Config.ROUNDED_CORNERS)
    val viewModel = viewModel<GroupsListViewModel>()

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = cardShape,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = cardShape)
            .clickable { didPressComponent(group) }

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(Config.MEDIUM_PADDING)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(id = group.icon.icon()),
                    modifier = Modifier
                        .size(Config.MEDIUM_ICON_SIZE)
                        .padding(Config.XSMALL_PADDING),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(Config.XSMALL_PADDING)
                ) {
                    Text(
                        text = group.name, style = MaterialTheme.typography.h4, color = AppTheme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.height(Config.XSMALL_PADDING))
                    Text(
                        text = "$ ${String.format(Locale.US, "%.2f", group.userBalance)}",
                        style = MaterialTheme.typography.subtitle2, color = AppTheme.colors.primaryText
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.End,
                ) {
                    IconButton(
                        onClick = { viewModel.markAsFavourite(group, !group.isFavourite) }) {
                        Image(
                            painterResource(id = icon),
                            contentDescription = "",
                        )
                    }
                    Spacer(modifier = Modifier.height(Config.LARGE_PADDING+Config.LARGE_PADDING))
                    Text(
                        text = dateFormat.format(group.createDate),
                        style = MaterialTheme.typography.subtitle2, color = AppTheme.colors.primaryText
                    )
                }
            }
        }
    }
}
