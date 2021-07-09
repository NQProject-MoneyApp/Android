package com.nqproject.MoneyApp.ui.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


sealed class LoginResult {
    object Success: LoginResult()
    class Failed(val error: String): LoginResult()
}

class LoginViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun login(username: String, password: String): LoginResult {
        _loading.value = true

        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.login(username, password)
        }
        _loading.value = false

        return when(result) {
            is SimpleResult.Error -> LoginResult.Failed(result.error)
            is SimpleResult.Success -> LoginResult.Success
        }
    }

}