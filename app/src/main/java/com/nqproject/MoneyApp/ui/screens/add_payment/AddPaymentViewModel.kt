package com.nqproject.MoneyApp.ui.screens.add_payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.ExpenseRepository
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.User

class AddPaymentViewModel : ViewModel() {
    private var initialized = false

    private val _loading = MutableLiveData(false)
    private val _groupMembers = MutableLiveData(emptyList<User>())

    val loading: LiveData<Boolean> = _loading
    val groupMembers: LiveData<List<User>> = _groupMembers

    fun init(group: Group) {
        if (initialized) return
        initialized = true

        _groupMembers.value =
            group.members.map { User(pk = it.pk, name = it.name, email = it.email, balance = 0.0) }
    }

//    suspend fun addExpense(groupId: Int, name: String, amount: Float, participants: List<User>):
//            SimpleResult<String> {
//        _loading.value = true
//        val result = ExpenseRepository.addExpense(
//            groupId, name = name, amount = amount, participants = participants
//        )
//        _loading.value = false
//        return result
//    }

    suspend fun deleteExpense(expense: Expense): SimpleResult<String> {
        _loading.value = true
        val result =
            ExpenseRepository.deleteExpense(expense.groupId, expense.pk)
        _loading.value = false
        return result
    }
}