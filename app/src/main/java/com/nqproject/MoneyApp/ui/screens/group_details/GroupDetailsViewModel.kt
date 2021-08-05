package com.nqproject.MoneyApp.ui.screens.group_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.GroupRepository
import com.nqproject.MoneyApp.repository.User
import kotlinx.coroutines.launch

class GroupDetailsViewModel: ViewModel() {

    var initialized = false

    private val _loading = MutableLiveData(false)
    private val _group = MutableLiveData<Group>(null)
    private val _groupDetails = MutableLiveData(emptyList<User>())
//    val groupDetails: LiveData<List<User>> = _groupDetails
    var group : LiveData<Group> = _group

    fun init(group: Group) {
        if(initialized) return
        initialized = true

        this._group.value = group
        _loading.value = false
    }

    fun updateGroup() {
        viewModelScope.launch {
            fetchGroup()
        }
    }

    private suspend fun fetchGroup(): SimpleResult<Group> {
        _loading.value = true
        val result = GroupRepository.fetchGroupDetails(group.value!!.id)
        _loading.value = false

        if (result is SimpleResult.Success) {
            _group.value = result.data!!
        }

        return result
    }

    suspend fun fetchGroupCode(groupId: Int): SimpleResult<String> {
        return GroupRepository.generateCode(groupId)
    }
}