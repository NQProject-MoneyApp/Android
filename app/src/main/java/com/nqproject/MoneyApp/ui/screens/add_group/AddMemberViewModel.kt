package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.MoneyAppClient.icons
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddMemberViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    private val _choosenUsers = MutableLiveData(emptyList<User>())
    private val _userFriends = MutableLiveData(emptyList<User>())

    val choosenUsers: LiveData<List<User>> = _choosenUsers
    val userFriends: LiveData<List<User>> = _userFriends
    val loading: LiveData<Boolean> = _loading

    init {
        viewModelScope.launch {
            friends()
        }
    }

    suspend fun friends(): SimpleResult<List<User>> {
        _loading.value = true
        val result = FriendsRepository.fetchFriends()
        if (result is SimpleResult.Success) {
            _userFriends.value = result.data
        }
        delay(2000)
        _loading.value = false
        return result
    }

    fun save(users: List<User>) {
        _choosenUsers.value = users
    }


}