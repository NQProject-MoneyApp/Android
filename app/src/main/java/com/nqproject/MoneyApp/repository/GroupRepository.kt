package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GroupRepository {

    suspend fun fetchGroups(): SimpleResult<List<Group>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.groups()
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map { Group(
                    id = it.pk!!,
                    name = it.name!!,
                    totalCost = it.total_cost!!,
                    icon = R.drawable.ic_burger,
                    isFavourite = it.is_favourite!!,
                    userBalance = it.user_balance!!,
                    createDate = it.create_date!!) })
        }
    }

    suspend fun addGroup(name: String): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.addGroup(name=name)
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }
}