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

    class Success<T>(val data: T) : SimpleResult<T>()
    class Error<T>(val error: String) : SimpleResult<T>()

}

object MoneyAppClient {

    private val client = Retrofit.Builder()
        .baseUrl("https://money-app-nqproject-staging.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
                .addInterceptor(Interceptor() {
                    val builder = it.request().newBuilder()
                    AuthenticationManager.token?.let { it1 ->
                        builder.addHeader(
                            "Authorization", it1
                        )
                    }
                    return@Interceptor it.proceed(builder.build())
                })
                .build()
        )
        .build()
        .create(MoneyAppApi::class.java)

    suspend fun login(username: String, password: String): SimpleResult<String> {
        return try {
            val result = client.login(NetworkLoginRequest(username = username, password = password))

            if (result.key != null) {
                SimpleResult.Success(result.key)
            } else {
                SimpleResult.Error("Unknown error, ${result.error}")
            }
        } catch (e: HttpException) {
            //TODO: parse error from backend
            Log.e(Config.MAIN_TAG, "Failed to login", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun registration(
        username: String,
        password: String,
        email: String
    ): SimpleResult<String> {
        return try {
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
        } catch (e: HttpException) {
            //TODO: parse error from backend
            Log.e(Config.MAIN_TAG, "Failed to registration", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun fetchGroups(): SimpleResult<List<NetworkGroupsResponse>> {
        return try {
            val result = client.groups()
            result.forEach { println(it.name) }
            println(result)
            SimpleResult.Success(result)

        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch groups", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun addGroup(name: String, icon: Int): SimpleResult<NetworkGroupsResponse> {
        return try {
            val result = client.addGroup(NetworkAddGroupRequest(name = name, icon = icon))

            SimpleResult.Success(result)

        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to add groups", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun addExpense(
        groupId: Int,
        name: String,
        amount: Float
    ): SimpleResult<NetworkExpensesResponse> {
        return try {
            val result =
                client.addExpense(groupId, NetworkAddExpenseRequest(name = name, amount = amount))

            SimpleResult.Success(result)

        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to add expense", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun editExpense(
        groupId: Int,
        expenseId: Int,
        name: String,
        amount: Float
    ): SimpleResult<NetworkExpensesResponse> {
        return try {
            val result =
                client.editExpense(
                    groupId, expenseId, NetworkAddExpenseRequest(name = name, amount = amount)
                )

            SimpleResult.Success(result)

        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to edit expense", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun deleteExpense(groupId: Int, expenseId: Int): SimpleResult<Boolean> {
        return try {
            val result = client.deleteExpense(groupId, expenseId)

            if (result.code() != 204) {
                Log.e(Config.MAIN_TAG, "Failed to delete expense, response code ${result.code()}")
                return SimpleResult.Error("Unknown error")
            }
            SimpleResult.Success(true)

        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to delete expense", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun groupUsers(groupId: Int): SimpleResult<List<NetworkGroupUsersResponse>> {
        return try {
            val result = client.groupUsers(groupId)
            result.forEach { println("$it.user.username $it.balance") }
            println(result)
            SimpleResult.Success(result)
        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group users", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun fetchExpenses(groupId: Int): SimpleResult<List<NetworkExpensesResponse>> {
        return try {
            val result = client.groupExpenses(groupId)
            SimpleResult.Success(result)
        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group expenses", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun fetchExpenseDetails(groupId: Int, expenseId: Int):
            SimpleResult<NetworkExpenseDetailsResponse> {
        return try {
            val result = client.fetchExpenseDetails(groupId, expenseId)
            SimpleResult.Success(result)
        } catch (e: HttpException) {
            Log.e(
                Config.MAIN_TAG, "Failed to fetch expense details, for group $groupId and " +
                        "expense $expenseId", e
            )
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun groupCode(groupId: Int): SimpleResult<NetworkGroupCodeResponse> {
        return try {
            val result = client.groupCode(NetworkGroupCodeRequest(groupId))
            SimpleResult.Success(result)
        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group code", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun joinToGroup(code: String): SimpleResult<Boolean> {
        return try {
            client.joinToGroup(code)
            SimpleResult.Success(true)
        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to join to group", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun icons(): SimpleResult<List<Int>> {
        return try {
            SimpleResult.Success(client.icons().icons)
        } catch (e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch icons", e)
            SimpleResult.Error("Unknown error")
        }
    }
}