package com.nqproject.MoneyApp.ui.screens.edit_expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.ExpenseDetails
import com.nqproject.MoneyApp.repository.ExpenseRepository


class EditExpenseViewModel : ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun editExpense(expense: ExpenseDetails, name: String, amount: Float):
            SimpleResult<String> {
        _loading.value = true
        val result =
            ExpenseRepository.editExpense(expense.groupId, expense.pk, name = name, amount = amount)
        _loading.value = false
        return result
    }

    suspend fun deleteExpense(expense: ExpenseDetails): SimpleResult<String> {
        _loading.value = true
        val result =
            ExpenseRepository.deleteExpense(expense.groupId, expense.pk)
        _loading.value = false
        return result
    }
}