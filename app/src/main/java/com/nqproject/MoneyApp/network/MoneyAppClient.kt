package com.nqproject.MoneyApp.network

import android.util.Log
import com.nqproject.MoneyApp.network.models.NetworkLoginRequest
import com.nqproject.MoneyApp.ui.screens.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException

sealed class SimpleResult<T> {

    class Success<T>(val data: T): SimpleResult<T>()
    class Error<T>(val error: String): SimpleResult<T>()

}

object MoneyAppClient {

    private val client = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build())
        .build()
        .create(MoneyAppApi::class.java)

    suspend fun login(username: String, password: String): SimpleResult<String> {
        return try {
            val result = client.login(NetworkLoginRequest(username = username, password = password))

            if(result.key != null) {
                SimpleResult.Success(result.key)
            } else {
                SimpleResult.Error("Unknown error, ${result.error}")
            }
        } catch(e: HttpException) {
            //TODO: parse error from backend
            Log.e("MONEY_APP", "Failed to login", e)
            SimpleResult.Error("Unknown error")
        }
    }
}