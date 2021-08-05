package com.nqproject.MoneyApp.ui.screens.add_expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.*


class AddExpenseViewModel : ViewModel() {
    var initialized = false

    private val _loading = MutableLiveData(false)
    private val _groupMembers = MutableLiveData(emptyList<User>())
    private val _chosenParticipants = MutableLiveData(emptyList<User>())

    val loading: LiveData<Boolean> = _loading
    val groupMembers: LiveData<List<User>> = _groupMembers
    val chosenParticipants: LiveData<List<User>> = _chosenParticipants

    fun init(group: Group, participants: List<User>? = null) {
        _loading.value = true
        if (initialized) return
        initialized = true

        _groupMembers.value =
            group.members.map { User(pk = it.pk, name = it.name, email = it.email, balance = 0.0) }
        _chosenParticipants.value = participants ?: _groupMembers.value
        _loading.value = false
    }

    suspend fun addExpense(groupId: Int, name: String, amount: Float): SimpleResult<String> {
        _loading.value = true
        val result = ExpenseRepository.addExpense(
            groupId, name = name, amount = amount, participants
            = _chosenParticipants.value!!
        )
        _loading.value = false
        return result
    }

    suspend fun editExpense(expense: Expense, name: String, amount: Float):
            SimpleResult<String> {
        _loading.value = true
        val result =
            ExpenseRepository.editExpense(
                expense.groupId, expense.pk, name = name, amount =
                amount, participants = _chosenParticipants.value!!
            )
        _loading.value = false
        return result
    }

    suspend fun deleteExpense(expense: Expense): SimpleResult<String> {
        _loading.value = true
        val result =
            ExpenseRepository.deleteExpense(expense.groupId, expense.pk)
        _loading.value = false
        return result
    }

    fun addChosenMember(user: User) {
        _chosenParticipants.value = _chosenParticipants.value?.plus(user)
    }

    fun removeChosenMember(user: User) {
        _chosenParticipants.value = _chosenParticipants.value?.filter { it.pk != user.pk }
    }
}