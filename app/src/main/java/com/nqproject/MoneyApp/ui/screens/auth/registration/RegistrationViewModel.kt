package com.nqproject.MoneyApp.ui.screens.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.repository.UserRepository

sealed class RegistrationResult {
    object Success: RegistrationResult()
    class Failed(val error: String): RegistrationResult()
}

class RegistrationViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun register(username: String, password: String, email: String): RegistrationResult {
        _loading.value = true
        val result = UserRepository.register(username = username, password = password, email = email)
        _loading.value = false

        return result
    }
}