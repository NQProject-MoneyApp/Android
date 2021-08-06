package com.nqproject.MoneyApp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.screens.add_group.AddUserComponent

@Suppress("UnnecessaryComposedModifier")
fun Modifier.bottomRectBorder(width: Dp = Dp.Hairline, color: Color = Color.Black):
        Modifier = composed(
    factory = {
        this.then(
            Modifier.drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawLine(
                        color,
                        Offset(width.value, size.height - width.value),
                        Offset(size.width - width.value, size.height - width.value),
                        strokeWidth = width.value
                    )
                }
            }
        )
    },
)

@Composable
fun ChooseUsersComponent(
    title: String,
    groupMembers: List<User>,
    chosenMembers: ValidableValue<List<User>>,
) {
    val errorMessage = chosenMembers.errorMessage.observeAsState().value!!
    val chosenMembersValue = chosenMembers.value.observeAsState().value!!

    val cardShape = RoundedCornerShape(10.dp)
    var cardModifier = Modifier
        .fillMaxWidth()

    if (errorMessage.isNotEmpty())
        cardModifier = cardModifier
            .clip(cardShape)
            .bottomRectBorder(3.dp, MaterialTheme.colors.error)


    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = cardShape,
        modifier = cardModifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))


            groupMembers.forEach { user ->
                AddUserComponent(
                    user = user,
                    check = chosenMembersValue.contains(user),
                    didPressComponent = {
                        if (chosenMembersValue.contains(user)) {
                            chosenMembers.updateValue(
                                chosenMembersValue.filter { it.pk != user.pk })
                        } else {
                            chosenMembers.updateValue(chosenMembersValue.plus(user))
                        }
                    })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
    )
}