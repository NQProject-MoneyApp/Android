package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.group_details.UserBalance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GroupRepository {

    suspend fun fetchGroups(): SimpleResult<List<Group>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.fetchGroups()
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map { Group(
                    id = it.pk!!,
                    name = it.name!!,
                    totalCost = it.total_cost!!,
                    icon = MoneyAppIcon.from(it.icon!!).icon(),
                    isFavourite = false,
                    userBalance = it.user_balance!!,
                    createDate = it.create_date!!) })
        }
    }

    suspend fun addGroup(name: String, icon: Int): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.addGroup(name=name, icon=icon)
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun fetchGroupUsers(groupId: Int): SimpleResult<List<UserBalance>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.groupUsers(groupId)
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map { UserBalance(name = it.user!!.username!!, balance = it.balance!!) })
                //TODO: Parse if not null
        }
    }

    suspend fun generateCode(groupId: Int): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.groupCode(groupId)
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(result.data.code)
        }
    }

    suspend fun join(code: String): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.joinToGroup(code)
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }
}