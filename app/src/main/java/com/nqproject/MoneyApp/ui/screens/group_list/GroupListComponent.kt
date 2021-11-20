package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.MoneyAppIcon
import com.nqproject.MoneyApp.ui.theme.AppTheme
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun GroupListComponent(
    group: Group,
    didPressComponent: () -> Unit,
    markAsFavourite: (favourite: Boolean) -> Unit,
) {
    val icon = if (group.isFavourite) R.drawable.ic_star_select
                else R.drawable.ic_star

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = StyleConfig.CARD_SHAPE,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = StyleConfig.CARD_SHAPE)
            .clickable { didPressComponent() }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(StyleConfig.XSMALL_PADDING)
        ) {
            Image(
                painterResource(id = group.icon.icon()),
                modifier = Modifier
                    .padding(start = StyleConfig.SMALL_PADDING)
                    .size(StyleConfig.MEDIUM_ICON_SIZE),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                start = StyleConfig.XSMALL_PADDING,
                                top = StyleConfig.SMALL_PADDING,
                            )
                    ) {
                        Text(
                            text = group.name,
                            style = MaterialTheme.typography.h4,
                            color = AppTheme.colors.primaryText
                        )
                        Spacer(modifier = Modifier.height(StyleConfig.XSMALL_PADDING))
                        Text(
                            text = "$ ${String.format(Locale.US, "%.2f", group.userBalance)}",
                            style = MaterialTheme.typography.subtitle2,
                            color = AppTheme.colors.primaryText
                        )

                    }
                    IconButton(
                        onClick = { markAsFavourite(!group.isFavourite) }) {
                        Image(
                            painterResource(id = icon),
                            contentDescription = "",
                        )
                    }
                }
                val dateFormat = SimpleDateFormat("dd-MM-yyy")
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = dateFormat.format(group.createDate),
                    style = MaterialTheme.typography.subtitle2,
                    color = AppTheme.colors.primaryText,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MoneyAppTheme(darkTheme = true) {
        GroupListComponent(
            group = Group(
                id = 1,
                name = "Test test",
                createDate = Date(),
                icon = MoneyAppIcon.BeerHamburger,
                isFavourite = false,
                members = emptyList(),
                totalCost = 1200.25,
                userBalance = -230.5,
            ),
            didPressComponent = {},
            markAsFavourite = {},
        )
    }
}

