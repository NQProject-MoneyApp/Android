package com.nqproject.MoneyApp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.nqproject.MoneyApp.StyleConfig
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.screens.add_group.AddUserComponent
import com.nqproject.MoneyApp.ui.theme.AppTheme


@Composable
fun ChooseUsersComponent(
    title: String,
    groupMembers: List<User>,
    chosenMembers: ValidableValue<List<User>>,
) {
    val errorMessage = chosenMembers.errorMessage.collectAsState().value
    val chosenMembersValue = chosenMembers.value.collectAsState().value

    val cardShape = RoundedCornerShape(StyleConfig.ROUNDED_CORNERS)
    var cardModifier = Modifier
        .fillMaxWidth()

    if (errorMessage.isNotEmpty())
        cardModifier = cardModifier
            .clip(cardShape)
            .bottomRectBorder(StyleConfig.BORDER_STROKE, MaterialTheme.colors.error)


    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = cardShape,
        modifier = cardModifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(StyleConfig.MEDIUM_PADDING)
        ) {
            Text(title, color = AppTheme.colors.primaryText, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(StyleConfig.SMALL_PADDING))


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
            }
        }
    }
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
    )
}