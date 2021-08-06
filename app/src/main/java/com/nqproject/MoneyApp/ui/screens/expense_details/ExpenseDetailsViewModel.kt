package com.nqproject.MoneyApp.ui.screens.expense_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.ExpenseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ExpenseDetailsViewModel: ViewModel() {

    private var initialized = false

    private val _loading = MutableLiveData(false)
    private val _expense = MutableLiveData<Expense>(null)

    val loading: LiveData<Boolean> = _loading

    var expense : LiveData<Expense> = _expense


    fun init (expense: Expense) {
        if(initialized) return
        initialized = true

        this._expense.value = expense

        _loading.value = false
    }

    fun updateExpense(noticeably: Boolean = false) {
        viewModelScope.launch {
            fetchExpense(noticeably)
        }
    }

    private suspend fun fetchExpense(noticeably: Boolean): SimpleResult<Expense> {
        val date = Date()
        _loading.value = true
        val result = ExpenseRepository.fetchExpenseDetails(expense.value!!.groupId, expense.value!!.pk)
        if (noticeably) delay(1000 - (Date().time-date.time))
        _loading.value = false

        if (result is SimpleResult.Success) {
            _expense.value = result.data!!
        }

        return result
    }
}