package com.nqproject.MoneyApp.ui.screens.expense_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseListViewModel: ViewModel() {

    private val _groupExpenses = MutableLiveData(emptyList<Expense>())
    val groupExpenses: LiveData<List<Expense>> = _groupExpenses

    fun init(groupId: Int) {
        updateExpenses(groupId)
    }

    fun updateExpenses(groupId: Int) {
        viewModelScope.launch {
            fetchExpenses(groupId)
        }
    }

    suspend fun fetchExpenses(groupId: Int): SimpleResult<List<Expense>> {
        val result = ExpenseRepository.fetchExpenses(groupId)
        if(result is SimpleResult.Success) {
            _groupExpenses.value = result.data
        }

        return result
    }

}