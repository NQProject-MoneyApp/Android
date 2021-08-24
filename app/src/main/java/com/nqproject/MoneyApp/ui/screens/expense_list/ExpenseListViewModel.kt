package com.nqproject.MoneyApp.ui.screens.expense_list

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.ExpenseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExpenseListViewModel(app: Application): AndroidViewModel(app) {

    var initialized = false

    private var groupId: Int = 0
    private val _firstLoad = MutableLiveData(true)
    private val _loading = MutableLiveData(false)
    private val _groupExpenses = MutableLiveData(emptyList<Expense>())


    val firstLoad: LiveData<Boolean> = _firstLoad
    val loading: LiveData<Boolean> = _loading
    val groupExpenses: LiveData<List<Expense>> = _groupExpenses

    fun init(groupId: Int) {
        if(initialized) return
        initialized = true

        this.groupId = groupId
    }

    fun updateExpenses(withLoader: Boolean = true) {
        viewModelScope.launch {
            fetchExpenses(groupId, withLoader)
        }
    }

    private suspend fun fetchExpenses(groupId: Int, withLoader: Boolean): SimpleResult<List<Expense>> {

        if (withLoader || _firstLoad.value == true)
            _loading.value = true
        val result = ExpenseRepository.fetchExpenses(groupId)

        if (withLoader || _firstLoad.value == true) {
            delay(1000)
            _loading.value = false
        }

        when(result) {
            is SimpleResult.Success -> {
                _groupExpenses.value = result.data
            }
            is SimpleResult.Error -> {
                Toast.makeText(getApplication(), result.error, Toast.LENGTH_SHORT).show()
            }
        }

        _firstLoad.value = false

        return result
    }
}