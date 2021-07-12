package com.nqproject.MoneyApp.ui.screens.group_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.GroupRepository

class GroupsListViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)
    private val _groupsList = MutableLiveData(emptyList<Group>())

    val loading: LiveData<Boolean> = _loading
    val groupsList: LiveData<List<Group>> = _groupsList

    suspend fun fetchGroups(): SimpleResult<List<Group>> {
        _loading.value = true
        val result = GroupRepository.fetchGroups()
        _loading.value = false

        if (result is SimpleResult.Success) {
            _groupsList.value = result.data
        }

        return result
    }
}