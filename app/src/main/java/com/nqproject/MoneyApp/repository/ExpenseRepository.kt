package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ExpenseRepository {

    suspend fun fetchExpenses(groupId: Int): SimpleResult<List<Expense>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.fetchExpenses(groupId)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map {
                    Expense(
                        pk = it.pk,
                        groupId = it.group_id,
                        name = it.name,
                        amount = it.amount,
                        author = User(it.author.username!!, it.author.email!!, 0.0),
                        createDate = it.create_date
                    )
                })
        }
    }

    suspend fun fetchExpenseDetails(groupId: Int, expenseId: Int): SimpleResult<ExpenseDetails> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.fetchExpenseDetails(groupId, expenseId)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                ExpenseDetails(
                    pk = result.data.pk,
                    groupId = result.data.group_id,
                    name = result.data.name,
                    amount = result.data.amount,
                    author = User(result.data.author.username!!, result.data.author.email!!, 0.0),
                    createDate = result.data.create_date
                )
            )
        }
    }

    suspend fun addExpense(groupId: Int, name: String, amount: Float): SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.addExpense(groupId, name = name, amount = amount)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun editExpense(groupId: Int, expenseId: Int, name: String, amount: Float):
            SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.editExpense(groupId, expenseId, name = name, amount = amount)
        }
        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }
}