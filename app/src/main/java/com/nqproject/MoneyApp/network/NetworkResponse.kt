package com.nqproject.MoneyApp.network

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.Type

//sealed class NetworkResponse<out T : Any, out U : Any> {
//    /**
//     * Success response with body
//     */
//    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()
//
//    /**
//     * Failure response with body
//     */
//    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()
//
//    /**
//     * Network error
//     */
//    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()
//
//    /**
//     * For example, json parsing error
//     */
//    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
//}

sealed class NetworkResponse<out T : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()


    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing>()
}

//class NetworkResponseAdapter<S : Any, E : Any>(
//    private val successType: Type,
//    private val errorBodyConverter: Converter<ResponseBody, E>
//) : CallAdapter<S, Call<NetworkResponse<S, E>>> {
//
//    override fun responseType(): Type = successType
//
//    override fun adapt(call: Call<S>): Call<NetworkResponse<S, E>> {
//        return NetworkResponseCall(call, errorBodyConverter)
//    }
//}
//
//internal class NetworkResponseCall<S : Any, E : Any>(
//    private val delegate: Call<S>,
//    private val errorConverter: Converter<ResponseBody, E>
//) : Call<NetworkResponse<S, E>> {
//
//    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
//        return delegate.enqueue(object : Callback<S> {
//            override fun onResponse(call: Call<S>, response: Response<S>) {
//                val body = response.body()
//                val code = response.code()
//                val error = response.errorBody()
//
//                if (response.isSuccessful) {
//                    if (body != null) {
//                        callback.onResponse(
//                            this@NetworkResponseCall,
//                            Response.success(NetworkResponse.Success(body))
//                        )
//                    } else {
//                        // Response is successful but the body is null
//                        callback.onResponse(
//                            this@NetworkResponseCall,
//                            Response.success(NetworkResponse.UnknownError(null))
//                        )
//                    }
//                } else {
//                    val errorBody = when {
//                        error == null -> null
//                        error.contentLength() == 0L -> null
//                        else -> try {
//                            errorConverter.convert(error)
//                        } catch (ex: Exception) {
//                            null
//                        }
//                    }
//                    if (errorBody != null) {
//                        callback.onResponse(
//                            this@NetworkResponseCall,
//                            Response.success(NetworkResponse.ApiError(errorBody, code))
//                        )
//                    } else {
//                        callback.onResponse(
//                            this@NetworkResponseCall,
//                            Response.success(NetworkResponse.UnknownError(null))
//                        )
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<S>, throwable: Throwable) {
//                val networkResponse = when (throwable) {
//                    is IOException -> NetworkResponse.NetworkError(throwable)
//                    else -> NetworkResponse.UnknownError(throwable)
//                }
//                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
//            }
//        })
//    }
//
//    override fun isExecuted() = delegate.isExecuted
//
//    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)
//
//    override fun isCanceled() = delegate.isCanceled
//
//    override fun cancel() = delegate.cancel()
//
//    override fun execute(): Response<NetworkResponse<S, E>> {
//        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
//    }
//
//    override fun request(): Request = delegate.request()
//    override fun timeout(): Timeout {
//        return Timeout.minTimeout(10000L, 10000L)
//    }
//}