package com.nqproject.MoneyApp.ui.screens.group_list
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.GroupRepository
import kotlinx.coroutines.launch

class GroupsListViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)
    private val _groupsList = MutableLiveData(emptyList<Group>())

    val loading: LiveData<Boolean> = _loading
    val groupsList: LiveData<List<Group>> = _groupsList

    init {
        updateGroups()
    }

    fun updateGroups() {
        viewModelScope.launch {
            fetchGroups()
        }
    }

    suspend fun fetchGroups(): SimpleResult<List<Group>> {
        _loading.value = true
        val result = GroupRepository.fetchGroups()
        _loading.value = false

        if (result is SimpleResult.Success) {
            _groupsList.value = result.data
        }

        return result
    }

    suspend fun join(code: String): SimpleResult<String> {
        val result = GroupRepository.join(code)

        return when(result) {
            is SimpleResult.Error -> {
                Log.d(Config.MAIN_TAG, "Error toast?")
                SimpleResult.Error(result.error)
            }
            is SimpleResult.Success -> {
                viewModelScope.launch {
                    updateGroups()
                }
                SimpleResult.Success("Success")
            }
        }
    }
}