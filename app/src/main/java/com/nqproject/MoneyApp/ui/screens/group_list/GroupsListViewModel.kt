package com.nqproject.MoneyApp.ui.screens.group_list

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.repository.GroupRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class GroupsListViewModel(app: Application) : AndroidViewModel(app) {

    private val _loading = MutableLiveData(false)
    private val _groupsList = MutableLiveData(emptyList<Group>())
    private val _firstLoad = MutableLiveData(true)

    val loading: LiveData<Boolean> = _loading
    val groupsList: LiveData<List<Group>> = _groupsList
    val firstLoad: LiveData<Boolean> = _firstLoad

    fun updateGroups(withLoader: Boolean = true) {
        viewModelScope.launch {
            fetchGroups(withLoader)
        }
    }

    private suspend fun fetchGroups(withLoader: Boolean): SimpleResult<List<Group>> {
        val date = Date()

        if (withLoader || _firstLoad.value == true)
            _loading.value = true
        val result = GroupRepository.fetchGroups()

        if (withLoader || _firstLoad.value == true) {
            // UI FIX
            // request must take at least one second for the activity indicator to load
            delay(1000 - (Date().time - date.time))
            _loading.value = false
        }


        when(result) {
            is SimpleResult.Success -> {
                _groupsList.value = result.data
            }
            is SimpleResult.Error -> {
                Toast.makeText(getApplication(), result.error, Toast.LENGTH_SHORT).show()
            }
        }

        _firstLoad.value = false

        return result
    }

    suspend fun join(code: String): SimpleResult<String> {
        val result = GroupRepository.join(code)


        return when (result) {
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

    fun markAsFavourite(group: Group, isFavourite: Boolean) {
        viewModelScope.launch {
            when (val result = GroupRepository.markGroupAsFavourite(group, isFavourite)) {
                is SimpleResult.Error -> {
                    Toast.makeText(getApplication(), result.error, Toast.LENGTH_SHORT).show()
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
}