package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(Config.XSMALL_PADDING)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = Config.SMALL_PADDING)
            ) {
                Image(
                    painterResource(id = group.icon.icon()),
                    modifier = Modifier
                        .size(Config.MEDIUM_ICON_SIZE),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = Config.XSMALL_PADDING, top = Config.SMALL_PADDING)
                    ) {
                        Text(
                            text = group.name, style = MaterialTheme.typography.h4,
                            color = AppTheme.colors.primaryText
                        )
                        Spacer(modifier = Modifier.height(Config.XSMALL_PADDING))
                        Text(
                            text = "$ ${String.format(Locale.US, "%.2f", group.userBalance)}",
                            style = MaterialTheme.typography.subtitle2,
                            color = AppTheme.colors.primaryText
                        )

                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        IconButton(
                            onClick = { viewModel.markAsFavourite(group, !group.isFavourite) }) {
                            Image(
                                painterResource(id = icon),
                                contentDescription = "",
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = Config.SMALL_PADDING, bottom = Config.XSMALL_PADDING)
                    ) {
                        Text(
                            text = dateFormat.format(group.createDate),
                            style = MaterialTheme.typography.subtitle2,
                            color = AppTheme.colors.primaryText,
                        )
                    }
                }
            }
        }
    }
}

