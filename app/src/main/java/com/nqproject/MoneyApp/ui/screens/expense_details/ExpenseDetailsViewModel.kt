package com.nqproject.MoneyApp.ui.screens.expense_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.ExpenseDetails
import com.nqproject.MoneyApp.repository.ExpenseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ExpenseDetailsViewModel() : ViewModel() {

    var initialized = false

    private val _loading = MutableLiveData(false)
    private val _expenseDetails = MutableLiveData<ExpenseDetails?>(null)

    val loading: LiveData<Boolean> = _loading
    val expenseDetails: LiveData<ExpenseDetails?> = _expenseDetails

    var groupId : Int? = null
    var expenseId : Int? = null


    fun init (groupId: Int, expenseId: Int) {
        if(initialized) return
        initialized = true

        this.groupId = groupId
        this.expenseId = expenseId

        updateExpense()
    }

    fun updateExpense() {
        viewModelScope.launch {
            fetchExpense()
        }
    }

    private suspend fun fetchExpense(): SimpleResult<ExpenseDetails> {
        val date = Date()
        _loading.value = true
        val result = ExpenseRepository.fetchExpenseDetails(groupId!!, expenseId!!)
        delay(1000 - (Date().time-date.time))
        _loading.value = false

        if (result is SimpleResult.Success) {
            _expenseDetails.value = result.data
        }

        return result
    }
}