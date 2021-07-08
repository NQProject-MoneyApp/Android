package com.nqproject.MoneyApp.ui.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.models.NetworkLoginRequest
import com.nqproject.MoneyApp.network.models.NetworkLoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


sealed class LoginResult {
    object Success: LoginResult()
    class Failed(val error: String): LoginResult()
}

class LoginViewModel: ViewModel() {

    private val _loading = MutableLiveData(false)

    val loading: LiveData<Boolean> = _loading

    suspend fun login(username: String, password: String): LoginResult {
        _loading.value = true

        val result = try {
            val result = withContext(Dispatchers.IO) {
                MoneyAppClient.client.login(NetworkLoginRequest(username = username, password = password))
            }
            if(result.key != null) {
                LoginResult.Success
            }
            else {
                LoginResult.Failed("Unknown error, ${result.error}")
            }
        } catch(e: HttpException) {
            //TODO: parse error from backend
            Log.e("MONEY_APP", "Failed to login", e)
            LoginResult.Failed("Unknown error")
        }

        _loading.value = false
        return result
    }

}