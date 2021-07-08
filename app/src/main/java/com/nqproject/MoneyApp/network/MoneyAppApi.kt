package com.nqproject.MoneyApp.network

import com.nqproject.MoneyApp.network.models.NetworkLoginRequest
import com.nqproject.MoneyApp.network.models.NetworkLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MoneyAppApi {

    @POST("api/login/")
    suspend fun login(@Body request: NetworkLoginRequest): NetworkLoginResponse

}