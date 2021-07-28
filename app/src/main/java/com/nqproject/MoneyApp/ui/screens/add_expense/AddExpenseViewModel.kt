package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.ExpenseRepository


class AddExpenseViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun addExpense(groupId: Int, name: String, amount: Float): SimpleResult<String> {
        _loading.value = true
        val result = ExpenseRepository.addExpense(groupId, name=name, amount=amount)
        _loading.value = false
        return result
    }

}