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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.ui.screens.add_group.AddUserComponent
import com.nqproject.MoneyApp.ui.theme.AppTheme


@Composable
fun ChooseUsersComponent(
    title: String,
    groupMembers: List<User>,
    chosenMembers: ValidableValue<List<User>>,
) {
    val errorMessage = chosenMembers.errorMessage.observeAsState().value!!
    val chosenMembersValue = chosenMembers.value.observeAsState().value!!

    val cardShape = RoundedCornerShape(Config.ROUNDED_CORNERS)
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
            modifier = Modifier.padding(Config.SMALL_PADDING)
        ) {
            Text(title, color = AppTheme.colors.primaryText)
            Spacer(modifier = Modifier.height(Config.SMALL_PADDING))


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