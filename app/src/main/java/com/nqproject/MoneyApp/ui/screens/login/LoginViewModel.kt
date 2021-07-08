package com.nqproject.MoneyApp.ui.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random


sealed class LoginResult {
    object Success: LoginResult()
    class Failed(val error: String): LoginResult()
}

class LoginViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun login(username: String, password: String): LoginResult {
        _loading.value = true
        delay(2000)
        _loading.value = false

        return if(Random.nextBoolean()) {
            LoginResult.Success
        } else {
            LoginResult.Failed("Invalid password")
        }
    }

}