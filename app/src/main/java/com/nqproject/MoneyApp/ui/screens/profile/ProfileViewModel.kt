package com.nqproject.MoneyApp.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.repository.*
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private var initialized = false

    private val _loading = MutableLiveData(false)
    private val _user = MutableLiveData<User>(null)

    val loading: LiveData<Boolean> = _loading
    val user: LiveData<User> = _user


    fun init() {
        if (initialized) return
        initialized = true
    }

    fun updateUserProfile() {
        viewModelScope.launch {
            fetchUser()
        }
    }

    suspend fun fetchUser(): SimpleResult<User> {
        _loading.value = true
        val result = UserRepository.user()
        if(result is SimpleResult.Success) {
            _user.value = result.data!!
        }
        _loading.value = false
        return result
    }

    suspend fun editUserProfile(pk: Int, name: String, email: String): SimpleResult<User> {
        _loading.value = true
        val result = UserRepository.editUser(pk, name, email)
        if(result is SimpleResult.Success) {
            _user.value = result.data!!
        }
        _loading.value = false
        return result
    }
}