package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.GroupRepository
import com.nqproject.MoneyApp.repository.User

class GroupDetailsViewModel: ViewModel() {

    var initialized = false

    private val _groupDetails = MutableLiveData(emptyList<User>())
    val groupDetails: LiveData<List<User>> = _groupDetails

    fun init() {
        if(initialized) return
        initialized = true
    }

    suspend fun fetchGroupUsers(groupId: Int): SimpleResult<List<User>> {
        val result = GroupRepository.fetchGroupUsers(groupId)
        if(result is SimpleResult.Success) {
            _groupDetails.value = result.data
        }

        return result
    }

    suspend fun fetchGroupCode(groupId: Int): SimpleResult<String> {
        return GroupRepository.generateCode(groupId)
    }
}