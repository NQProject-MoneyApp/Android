package com.nqproject.MoneyApp.ui.screens.add_group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddGroupViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)
    private val _addGrouploading = MutableLiveData(false)
    private val _icons = MutableLiveData(emptyList<MoneyAppIcon>())

    private val _chosenUsers = MutableLiveData(emptyList<User>())
    private val _userFriends = MutableLiveData(emptyList<User>())

    val userFriends: LiveData<List<User>> = _userFriends
    val chosenUsers: LiveData<List<User>> = _chosenUsers
    val icons: LiveData<List<MoneyAppIcon>> = _icons
    val loading: LiveData<Boolean> = _loading
    val addGroupLoading: LiveData<Boolean> = _addGrouploading

    init {
        viewModelScope.launch {
            _loading.value = true
            friends()
            fetchIcons()
            _loading.value = false
        }
    }

    suspend fun addGroup(name: String, icon: MoneyAppIcon, members: List<User>): SimpleResult<String> {
        _addGrouploading.value = true
        val result = GroupRepository.addGroup(name=name, icon=icon.id, members = members)
        _addGrouploading.value = false
        return result
    }

    fun addChosenMember(user: User) {
        _chosenUsers.value = _chosenUsers.value?.plus(user)
    }

    fun removeChosenMember(user: User) {
        _chosenUsers.value = _chosenUsers.value?.filter { it.pk != user.pk }
    }

    private suspend fun fetchIcons(): SimpleResult<List<MoneyAppIcon>> {
        val result = IconsRepository.icons()
        if (result is SimpleResult.Success) {
            _icons.value = result.data
        }

        return result
    }

    private suspend fun friends(): SimpleResult<List<User>> {
        val result = FriendsRepository.fetchFriends()
        if (result is SimpleResult.Success) {
            _userFriends.value = result.data
        }
        return result
    }
}