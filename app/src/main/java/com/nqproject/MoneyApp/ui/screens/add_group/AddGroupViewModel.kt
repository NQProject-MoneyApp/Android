package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.GroupRepository

class AddGroupViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun addGroup(name: String): SimpleResult<String> {
        _loading.value = true
        val result = GroupRepository.addGroup(name=name)
        _loading.value = false
        return result
    }
}