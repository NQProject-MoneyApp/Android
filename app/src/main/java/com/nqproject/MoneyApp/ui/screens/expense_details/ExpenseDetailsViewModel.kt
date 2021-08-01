package com.nqproject.MoneyApp.ui.screens.expense_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.ExpenseDetails
import com.nqproject.MoneyApp.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseDetailsViewModel() : ViewModel() {

    var initialized = false

    private val _expenseDetails = MutableLiveData<ExpenseDetails?>(null)
    val expenseDetails: LiveData<ExpenseDetails?> = _expenseDetails


    fun init (groupId: Int, expenseId: Int) {
        if(initialized) return
        initialized = true

        updateExpense(groupId,expenseId)
    }

    fun updateExpense(groupId: Int, expenseId: Int) {
        viewModelScope.launch {
            fetchExpense(groupId,expenseId)
        }
    }

    private suspend fun fetchExpense(groupId: Int, expenseId: Int): SimpleResult<ExpenseDetails> {
        val result = ExpenseRepository.fetchExpenseDetails(groupId, expenseId)
        if (result is SimpleResult.Success) {
            _expenseDetails.value = result.data
        }

        return result
    }
}