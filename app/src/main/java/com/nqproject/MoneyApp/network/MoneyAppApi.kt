package com.nqproject.MoneyApp.network

import com.nqproject.MoneyApp.network.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoneyAppApi {

    @POST("api/login/")
    suspend fun login(@Body request: NetworkLoginRequest): NetworkLoginResponse

    @POST("api/registration/")
    suspend fun registration(@Body request: NetworkRegistrationRequest): NetworkRegistrationResponse

    @GET("api/groups/")
    suspend fun groups(): List<NetworkGroupsResponse>

    @POST("api/groups/")
    suspend fun addGroup(@Body request: NetworkAddGroupRequest): NetworkGroupsResponse
}