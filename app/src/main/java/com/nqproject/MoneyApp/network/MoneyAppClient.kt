package com.nqproject.MoneyApp.network

import android.util.Log
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.network.models.*
import okhttp3.Interceptor
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
        .baseUrl("http://192.168.1.150:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
            .addInterceptor(Interceptor() {
                val builder = it.request().newBuilder()
                AuthenticationManager.token?.let { it1 -> builder.addHeader("Authorization", it1) }
                return@Interceptor it.proceed(builder.build())
            })
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
            Log.e(Config.MAIN_TAG, "Failed to login", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun registration(username: String, password: String, email: String): SimpleResult<String> {
        return try {
            val result = client.registration(NetworkRegistrationRequest(username = username, email = email, password1 = password, password2 = password,))

            if(result.key != null) {
                SimpleResult.Success(result.key)
            } else {
                SimpleResult.Error("Unknown error, ${result.error}")
            }
        } catch(e: HttpException) {
            //TODO: parse error from backend
            Log.e(Config.MAIN_TAG, "Failed to registration", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun groups(): SimpleResult<List<NetworkGroupsResponse>> {
        return try {
            val result = client.groups()
            result.forEach { println(it.name) }
            println(result)
            SimpleResult.Success(result)

        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch groups", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun addGroup(name: String): SimpleResult<NetworkGroupsResponse> {
        return try {
            val result = client.addGroup(NetworkAddGroupRequest(name=name))

            SimpleResult.Success(result)

        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch groups", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun groupUsers(groupId: Int): SimpleResult<List<NetworkGroupUsersResponse>> {
        return try {
            val result = client.groupUsers(groupId)
            result.forEach { println("$it.username $it.balance") }
            println(result)
            SimpleResult.Success(result)
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group users", e)
            SimpleResult.Error("Unknown error")
        }
    }
}