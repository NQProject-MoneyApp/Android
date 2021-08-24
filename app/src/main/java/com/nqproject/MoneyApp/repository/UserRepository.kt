package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import com.nqproject.MoneyApp.ui.screens.auth.login.LoginResult
import com.nqproject.MoneyApp.ui.screens.auth.registration.RegistrationResult
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

    suspend fun register(username: String, email: String, password: String): RegistrationResult {

        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.registration(username, password, email)
        }

        return when (result) {
            is SimpleResult.Error -> RegistrationResult.Failed(result.error)
            is SimpleResult.Success -> RegistrationResult.Success
        }
    }

    suspend fun user(): SimpleResult<User> {

        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.user()
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                User(
                    pk = result.data.pk!!,
                    name = result.data.username,
                    email = result.data.email,
                    balance = null,
                )
            )
        }
    }

    suspend fun editUser(pk: Int, name: String, email: String): SimpleResult<User> {

        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.editUser(pk, name, email)
        }

        return when (result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                User(
                    pk = result.data.pk!!,
                    name = result.data.username,
                    email = result.data.email,
                    balance = null,
                )
            )
        }
    }
}