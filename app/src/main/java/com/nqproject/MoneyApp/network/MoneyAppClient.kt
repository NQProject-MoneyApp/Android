package com.nqproject.MoneyApp.network
import android.util.Log
import com.nqproject.MoneyApp.Config
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.network.models.*
import com.nqproject.MoneyApp.repository.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

sealed class SimpleResult<T> {

    class Success<T>(val data: T): SimpleResult<T>()
    class Error<T>(val error: String): SimpleResult<T>()
}

class ConnectivityInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(Config.MAIN_TAG, "intercept")

        try {
            val response = chain.proceed(chain.request())
            // TODO check 413
            return response

        } catch (t: UnknownHostException) {
            // throw error??
//            throw HttpException(
//                retrofit2.Response.error<Any>(409, ""
//                    .toResponseBody("plain/text".toMediaTypeOrNull()),
//                )
//            )
            throw NoConnectivityException()
        }
    }
}

object MoneyAppClient {

    private val client = Retrofit.Builder()
        .baseUrl("https://money-app-nqproject-staging.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
            .callTimeout(Config.SOCKET_TIMEOUT)
            .connectTimeout(Config.SOCKET_TIMEOUT)
            .readTimeout(Config.SOCKET_TIMEOUT)
            .writeTimeout(Config.SOCKET_TIMEOUT)
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
        } catch (e: NoConnectivityException) {
            Log.e(Config.MAIN_TAG, "No internet", e)
            SimpleResult.Error(e.message)
        }
    }

    suspend fun registration(username: String, password: String, email: String): SimpleResult<String> {
        return try {
            val result = client.registration(NetworkRegistrationRequest(
                username = username,
                email = email,
                password1 = password,
                password2 = password
            ))

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

    suspend fun addGroup(name: String, icon: Int, members: List<User>): SimpleResult<NetworkGroupsResponse> {
        return try {
            val result = client.addGroup(
                NetworkAddGroupRequest(
                    name=name,
                    icon=icon,
                    members = members.map { it.pk } )
            )
            SimpleResult.Success(result)

        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to add groups", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun addExpense(groupId: Int, name: String, amount: Float): SimpleResult<NetworkExpensesResponse> {
        return try {
            val result = client.addExpense(groupId, NetworkAddExpenseRequest(name=name, amount=amount))

            SimpleResult.Success(result)

        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to add expense", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun groupUsers(groupId: Int): SimpleResult<List<NetworkGroupUsersResponse>> {
        return try {
            val result = client.groupUsers(groupId)
            result.forEach { println("$it.user.username $it.balance") }
            println(result)
            SimpleResult.Success(result)
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group users", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun expenses(groupId: Int): SimpleResult<List<NetworkExpensesResponse>> {
        return try {
            val result = client.groupExpenses(groupId)
            SimpleResult.Success(result)
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group expenses", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun groupCode(groupId: Int): SimpleResult<NetworkGroupCodeResponse> {
        return try {
            val result = client.groupCode(NetworkGroupCodeRequest(groupId))
            SimpleResult.Success(result)
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch group code", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun joinToGroup(code: String): SimpleResult<Boolean> {
        return try {
            client.joinToGroup(code)
            SimpleResult.Success(true)
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to join to group", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun icons(): SimpleResult<List<Int>> {
        return try {
            SimpleResult.Success(client.icons().icons)
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch icons", e)
            SimpleResult.Error("Unknown error")
        }
    }

    suspend fun friends(): SimpleResult<List<NetworkUser>> {
        return try {
            SimpleResult.Success(client.friends())
        } catch(e: HttpException) {
            Log.e(Config.MAIN_TAG, "Failed to fetch friends", e)
            SimpleResult.Error("Unknown error")
        }
    }
}