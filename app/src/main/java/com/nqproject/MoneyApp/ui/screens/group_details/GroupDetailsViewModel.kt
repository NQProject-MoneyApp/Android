package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Expense
import com.nqproject.MoneyApp.repository.ExpenseRepository
import com.nqproject.MoneyApp.repository.User
import com.nqproject.MoneyApp.repository.GroupRepository

class GroupDetailsViewModel: ViewModel() {

    private val _groupDetails = MutableLiveData(emptyList<UserBalance>())
    val groupDetails: LiveData<List<UserBalance>> = _groupDetails

    suspend fun fetchGroupUsers(groupId: Int): SimpleResult<List<UserBalance>> {
        val result = GroupRepository.fetchGroupUsers(groupId)
        if(result is SimpleResult.Success) {
            _groupDetails.value = result.data
        }

        return result
    }
}