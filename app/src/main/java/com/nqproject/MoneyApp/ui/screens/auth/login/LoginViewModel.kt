package com.nqproject.MoneyApp.ui.screens.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.repository.UserRepository

sealed class LoginResult {
    object Success: LoginResult()
    class Failed(val error: String): LoginResult()
}

class LoginViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun login(username: String, password: String): LoginResult {
        _loading.value = true
        val result = UserRepository.login(username = username, password = password)
        _loading.value = false

        return result
    }
}