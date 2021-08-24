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
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun GroupListComponent(group: Group, didPressComponent: (Group) -> Unit) {
    val icon = if (group.isFavourite) R.drawable.ic_star_select else R.drawable.ic_star
    val dateFormat = SimpleDateFormat("dd-MM-yyy")
    val cardShape = RoundedCornerShape(15)
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
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(id = group.icon.icon()),
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = group.name, style = MaterialTheme.typography.h4, color = AppTheme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                    Spacer(modifier = Modifier.height(65.dp))
                    Text(
                        text = dateFormat.format(group.createDate),
                        style = MaterialTheme.typography.subtitle2, color = AppTheme.colors.primaryText
                    )
                }
            }
        }
    }
}
