package com.nqproject.MoneyApp.network

import com.nqproject.MoneyApp.network.models.NetworkAddGroupRequest
import com.nqproject.MoneyApp.network.models.NetworkGroupsResponse
import com.nqproject.MoneyApp.network.models.NetworkLoginRequest
import com.nqproject.MoneyApp.network.models.NetworkLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoneyAppApi {

    @POST("api/login/")
    suspend fun login(@Body request: NetworkLoginRequest): NetworkLoginResponse

    @GET("api/groups/")
    suspend fun groups(): List<NetworkGroupsResponse>

    @POST("api/groups/")
    suspend fun addGroup(@Body request: NetworkAddGroupRequest): NetworkGroupsResponse
}