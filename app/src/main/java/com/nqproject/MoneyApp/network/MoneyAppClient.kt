package com.nqproject.MoneyApp.network

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.network.models.*
import com.nqproject.MoneyApp.repository.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import java.io.IOException
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.Exception

sealed class SimpleResult<T> {

    class Success<T>(val data: T) : SimpleResult<T>()
    class Error<T>(val error: String) : SimpleResult<T>()

}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
    withContext(Dispatchers.IO) { string() }

object MoneyAppClient {

    var logoutCallback: (() -> Unit)? = null

    private val client = Retrofit.Builder()
        .baseUrl("https://money-app-nqproject-staging.herokuapp.com")
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
            isLenient
        }.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(Interceptor() {
                    val builder = it.request().newBuilder()
                    AuthenticationManager.token?.let { token ->
                        builder.addHeader(
                            "Authorization", token
                        )
                    }
                    return@Interceptor it.proceed(builder.build())
                })
                .addInterceptor {
                    val builder = it.request().newBuilder()
                    val result = it.proceed(builder.build())
                    if (result.code == 401 && AuthenticationManager.isLoggedIn) {
                        logoutCallback?.invoke()
                    }
                    return@addInterceptor result
                }
                .addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()
        )
        .build()
        .create(MoneyAppApi::class.java)

    private suspend fun <T> runRequest(
        onError: (suspend (e: HttpException) -> SimpleResult<T>)? = null,
        request: suspend () -> SimpleResult<T>,
    ): SimpleResult<T> {
        return try {
            return request()
        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to run request", e)
            onError?.invoke(e) ?: SimpleResult.Error("Unknown error")
        } catch (e: IOException) {
            Log.e(Config.MAIN_TAG, "Failed to run request", e)
            SimpleResult.Error("Something failed, check your internet connection.")
        } catch(e: Exception) {
            Log.e(Config.MAIN_TAG, "Failed to run request", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun login(username: String, password: String): SimpleResult<String> {
        return runRequest {
            val result = client.login(NetworkLoginRequest(username = username, password = password))

            if (result.key != null) {
                SimpleResult.Success(result.key)
            } else {
                SimpleResult.Error("Unknown error, ${result.error}")
            }
        }
    }

    suspend fun registration(
        username: String,
        password: String,
        email: String
    ): SimpleResult<String> {
        return runRequest {
            val result = client.registration(
                NetworkRegistrationRequest(
                    username = username, email = email, password1 = password, password2 = password,
                )
            )

            if (result.key != null) {
                SimpleResult.Success(result.key)
            } else {
                SimpleResult.Error("Unknown error, ${result.error}")
            }
        }
    }

    suspend fun fetchGroups(): SimpleResult<List<NetworkGroupsResponse>> {
        return runRequest {
            val result = client.groups()
            result.forEach {
                println(it.name)
            }
            println(result)
            SimpleResult.Success(result)
        }
    }

    suspend fun fetchGroupDetails(groupId: Int):
            SimpleResult<NetworkGroupsResponse> {
        return runRequest {
            val result = client.fetchGroupDetails(groupId)
            SimpleResult.Success(result)
        }
    }

    suspend fun addGroup(
        name: String,
        icon: Int,
        members: List<User>
    ): SimpleResult<NetworkGroupsResponse> {
        return runRequest {
            val result = client.addGroup(
                NetworkAddGroupRequest(
                    name = name,
                    icon = icon,
                    members = members.map { it.pk })
            )
            SimpleResult.Success(result)
        }
    }

    suspend fun editGroup(
        groupId: Int,
        isFavourite: Boolean
    ): SimpleResult<NetworkGroupsResponse> {
        return runRequest {
            val result = client.editGroup(
                groupId,
                NetworkAddGroupRequest(isFavourite = isFavourite)
            )
            SimpleResult.Success(result)
        }
    }

    suspend fun addExpense(
        groupId: Int,
        name: String,
        amount: Float,
        participants: List<User>,
    ): SimpleResult<NetworkExpensesResponse> {
        return runRequest {
            val result =
                client.addExpense(
                    groupId, NetworkAddExpenseRequest(
                        name = name, amount = amount, participants = participants.map { it.pk })
                )

            SimpleResult.Success(result)
        }
    }

    suspend fun editExpense(
        groupId: Int,
        expenseId: Int,
        name: String,
        amount: Float,
        participants: List<User>,
    ): SimpleResult<NetworkExpensesResponse> {
        return runRequest {
            val result =
                client.editExpense(
                    groupId, expenseId, NetworkAddExpenseRequest(name = name, amount = amount,
                        participants = participants.map { it.pk })
                )

            SimpleResult.Success(result)
        }
    }

    suspend fun deleteExpense(groupId: Int, expenseId: Int): SimpleResult<Boolean> {
        return runRequest {
            val result = client.deleteExpense(groupId, expenseId)

            if (result.code() != 204) {
                Log.e(Config.MAIN_TAG, "Failed to delete expense, response code ${result.code()}")
                return@runRequest SimpleResult.Error("Unknown error")
            }
            SimpleResult.Success(true)
        }
    }

    suspend fun fetchExpenses(groupId: Int): SimpleResult<List<NetworkExpensesResponse>> {
        return runRequest {
            val result = client.groupExpenses(groupId)
            SimpleResult.Success(result)
        }
    }

    suspend fun fetchExpenseDetails(groupId: Int, expenseId: Int):
            SimpleResult<NetworkExpensesResponse> {

        return runRequest {
            val result = client.fetchExpenseDetails(groupId, expenseId)
            SimpleResult.Success(result)
        }
    }

    suspend fun groupCode(groupId: Int): SimpleResult<NetworkGroupCodeResponse> {
        return runRequest {
            val result = client.groupCode(NetworkGroupCodeRequest(groupId))
            SimpleResult.Success(result)
        }
    }

    suspend fun joinToGroup(code: String): SimpleResult<Boolean> {
        return runRequest(
            request = {
                client.joinToGroup(code)
                SimpleResult.Success(true)
            },
            onError = { e ->
                Log.e(Config.MAIN_TAG, "Failed to join to group", e)
                val errorContent = e.response()?.errorBody()?.stringSuspending()
                try {
                    val message = Json.decodeFromString<ErrorResponse>(errorContent ?: "").details
                    return@runRequest SimpleResult.Error(message!!)
                }
                catch (e: Exception) {
                    return@runRequest SimpleResult.Error("Unknown error")
                }
            }
        )
    }

    suspend fun icons(): SimpleResult<List<Int>> {
        return runRequest {
            SimpleResult.Success(client.icons().icons)
        }
    }

    suspend fun friends(): SimpleResult<List<NetworkUser>> {
        return runRequest {
            SimpleResult.Success(client.friends())
        }
    }
}