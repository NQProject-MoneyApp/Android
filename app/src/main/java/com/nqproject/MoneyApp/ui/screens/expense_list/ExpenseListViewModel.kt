package com.nqproject.MoneyApp.ui.screens.expense_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.ExpenseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExpenseListViewModel: ViewModel() {

    var initialized = false

    private var groupId: Int = 0
    private val _loading = MutableLiveData(false)
    private val _groupExpenses = MutableLiveData(emptyList<Expense>())

    val loading: LiveData<Boolean> = _loading
    val groupExpenses: LiveData<List<Expense>> = _groupExpenses

    fun init(groupId: Int) {
        if(initialized) return
        initialized = true

        this.groupId = groupId
    }

    fun updateExpenses() {
        viewModelScope.launch {
            fetchExpenses(groupId)
        }
    }

    suspend fun fetchExpenses(groupId: Int): SimpleResult<List<Expense>> {
        _loading.value = true
        val result = ExpenseRepository.fetchExpenses(groupId)
        // TODO
        delay(1000)
        _loading.value = false
        if(result is SimpleResult.Success) {
            _groupExpenses.value = result.data
        }

        return result
    }

}