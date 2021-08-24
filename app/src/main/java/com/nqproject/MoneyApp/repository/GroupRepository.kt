package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GroupRepository {

    suspend fun fetchGroups(): SimpleResult<List<Group>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.fetchGroups()
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map {
                    Group(
                        id = it.pk,
                        name = it.name,
                        totalCost = it.totalCost,
                        icon = MoneyAppIcon.from(it.icon).icon(),
                        userBalance = it.userBalance,
                        createDate = DateUtils.parseDate(it.createDate),
                        isFavourite = it.isFavourite,
                        members = it.members.map { member ->
                            User(
                                pk = member.user.pk, name = member.user.username,
                                email = member.user.email, balance = member.balance
                            )
                        }
                    )
            })
        }
    }

    suspend fun markGroupAsFavourite(group: Group, isFavourite: Boolean): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.editGroup(group.id, group.name, group.icon, isFavourite = isFavourite)
        }
        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun fetchGroupDetails(groupId: Int): SimpleResult<Group> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.fetchGroupDetails(groupId)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                Group(
                    id = result.data.pk,
                    name = result.data.name,
                    totalCost = result.data.totalCost,
                    icon = MoneyAppIcon.from(result.data.icon).icon(),
                    userBalance = result.data.userBalance,
                    createDate = DateUtils.parseDate(result.data.createDate),
                    members = result.data.members.map { member ->
                        User(
                            pk = member.user.pk, name = member.user.username,
                            email = member.user.email, balance = member.balance
                        )
                    },
                    isFavourite = result.data.isFavourite
                )
            )
        }
    }

    suspend fun addGroup(name: String, icon: Int, members: List<User>): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.addGroup(name = name, icon = icon, members = members)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun editGroup(
        groupId: Int, name: String, icon: Int, isFavourite: Boolean): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.editGroup(
                groupId = groupId, name = name, icon = icon,
                isFavourite = isFavourite)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun generateCode(groupId: Int): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.groupCode(groupId)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(result.data.code)
        }
    }

    suspend fun join(code: String): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.joinToGroup(code)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }
}