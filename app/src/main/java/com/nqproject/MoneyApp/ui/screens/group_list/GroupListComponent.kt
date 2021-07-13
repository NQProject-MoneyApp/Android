package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.repository.Group


@Composable
fun GroupListComponent(group: Group) {
    val icon = if (group.isFavourite) R.drawable.ic_star_select else R.drawable.ic_star

    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(
                    painterResource(id = group.icon),
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)) {
                    Text(text = group.name, style = MaterialTheme.typography.h5, color= Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "$ - ${group.amount}", style = MaterialTheme.typography.subtitle2, color= Color.White)
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.End,
                ) {
                    Image(painterResource(id = icon), contentDescription = "")
                    Spacer(modifier = Modifier.height(65.dp))
                    Text(text = "21-07-2021", style = MaterialTheme.typography.subtitle2, color= Color.White)
                }
            }
        }
    }
}
