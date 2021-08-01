package com.nqproject.MoneyApp.ui.screens.edit_expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.ExpenseRepository


class EditExpenseViewModel : ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun editExpense(groupId: Int, expenseId: Int, name: String, amount: Float):
            SimpleResult<String> {
        _loading.value = true
        val result = ExpenseRepository.editExpense(groupId, expenseId, name = name, amount = amount)
        _loading.value = false
        return result
    }
}