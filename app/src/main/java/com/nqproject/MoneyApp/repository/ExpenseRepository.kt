package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ExpenseRepository {

    suspend fun fetchExpenses(groupId: Int): SimpleResult<List<Expense>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.expenses(groupId)
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map { Expense(
                    name = it.name!!,
                    amount = it.amount!!,
                    author = User(it.author.username!!, it.author.email!!, 0.0),
                    createDate = it.create_date!!) })
        }
    }
}