package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
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
                        id = it.pk!!,
                        name = it.name!!,
                        totalCost = it.total_cost!!,
                        icon = MoneyAppIcon.from(it.icon!!).icon(),
                        userBalance = it.user_balance!!,
                        createDate = it.create_date!!,
                        isFavourite = it.isFavourite!!,
                        members = it.members.map { member ->
                            User(
                                pk = member.user.pk, name = member.user.username,
                                email = member.user.email, balance = member.balance!!
                            )
                        }
                    )
                })
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

    suspend fun markGroupAsFavourite(groupId: Int, isFavourite: Boolean): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.editGroup(groupId = groupId, isFavourite = isFavourite)
        }
        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun fetchGroupUsers(groupId: Int): SimpleResult<List<User>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.groupUsers(groupId)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map {
                    User(
                        pk = it.user.pk, name = it.user.username,
                        email = it.user.email, balance = it.balance!!
                    )
                })
            //TODO: Parse if not null
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