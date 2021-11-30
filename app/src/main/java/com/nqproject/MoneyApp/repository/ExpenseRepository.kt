package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.network.models.ExpenseType
import com.nqproject.MoneyApp.utils.DateUtils
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
                        groupId = it.groupId,
                        name = it.name,
                        amount = it.amount,
                        paidBy = it.paid_by,
                        paymentTo = it.payment_to,
                        type = ExpenseType.from(it.type),
                        author = User(pk = it.author.pk, it.author.username, it.author.email, 0.0),
                        createDate = DateUtils.parseDate(it.createDate),
                        participants = it.participants.map {
                            User(pk = it.pk, it.username, it.email, 0.0)
                        }
                    )
                })
        }
    }

    suspend fun fetchExpenseDetails(groupId: Int, expenseId: Int): SimpleResult<Expense> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.fetchExpenseDetails(groupId, expenseId)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                Expense(
                    pk = result.data.pk,
                    groupId = result.data.groupId,
                    name = result.data.name,
                    amount = result.data.amount,
                    author = User(
                        result.data.author.pk, result.data.author.username,
                        result.data.author.email, 0.0
                    ),
                    paidBy = result.data.paid_by,
                    paymentTo = result.data.payment_to,
                    type = ExpenseType.from(result.data.type),
                    createDate = DateUtils.parseDate(result.data.createDate),
                    participants = result.data.participants.map {
                        User(
                            pk = it.pk, it.username,
                            it.email, 0.0
                        )
                    }
                )
            )
        }
    }

    suspend fun addExpense(
        groupId: Int,
        name: String,
        amount: Float,
        participants: List<User>? = null,
        paidById: Int,
        paidToId: Int? = null,
        type: ExpenseType = ExpenseType.Expense
    ):
            SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.addExpense(
                groupId,
                name = name,
                amount = amount,
                participants = participants,
                paidById = paidById,
                paidToId = paidToId,
                type = type
            )
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun editExpense(
        groupId: Int,
        expenseId: Int,
        name: String,
        amount: Float,
        participants: List<User>,
        paidById: Int,
        type: ExpenseType = ExpenseType.Expense
    ):
            SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.editExpense(
                groupId,
                expenseId,
                name = name,
                amount = amount,
                participants = participants,
                paidById = paidById,
                type = type
            )
        }
        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }

    suspend fun deleteExpense(groupId: Int, expenseId: Int):
            SimpleResult<String> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.deleteExpense(groupId, expenseId)
        }
        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success("Success")
        }
    }
}