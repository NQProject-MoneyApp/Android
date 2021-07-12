package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {

    suspend fun login(username: String, password: String): LoginResult {

        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.login(username, password)
        }

        return when (result) {
            is SimpleResult.Error -> LoginResult.Failed(result.error)
            is SimpleResult.Success -> {
                AuthenticationManager.token = result.data
                LoginResult.Success
            }
        }
    }

    //    TODO
    suspend fun register(username: String, email: String, password: String) {}
}