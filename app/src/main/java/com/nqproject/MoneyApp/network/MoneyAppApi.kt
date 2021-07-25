package com.nqproject.MoneyApp.network

import com.nqproject.MoneyApp.network.models.*
import retrofit2.http.*

interface MoneyAppApi {

    @POST("api/login/")
    suspend fun login(@Body request: NetworkLoginRequest): NetworkLoginResponse

    @POST("api/registration/")
    suspend fun registration(@Body request: NetworkRegistrationRequest): NetworkRegistrationResponse

    @GET("api/groups/")
    suspend fun groups(): List<NetworkGroupsResponse>

    @POST("api/groups/")
    suspend fun addGroup(@Body request: NetworkAddGroupRequest): NetworkGroupsResponse

    @GET("/api/group/{id}/users/")
    suspend fun groupUsers(@Path("id") id: Int): List<NetworkGroupUsersResponse>

    @GET("/api/{id}/expenses/")
    suspend fun groupExpenses(@Path("id") id: Int): List<NetworkExpensesResponse>

    @POST("/api/group-codes/")
    suspend fun groupCode(@Body request: NetworkGroupCodeRequest): NetworkGroupCodeResponse

    @PUT("/api/join/{code}/")
    suspend fun joinToGroup(@Path("code") code: String)
}