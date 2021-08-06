package com.nqproject.MoneyApp.network

import com.nqproject.MoneyApp.network.models.*
import retrofit2.Response
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

    @PATCH("api/groups/{groupId}/")
    suspend fun editGroup(@Path("groupId") groupId: Int, @Body request: NetworkAddGroupRequest):
            NetworkGroupsResponse

    @POST("/api/{id}/expenses/")
    suspend fun addExpense(
        @Path("id") id: Int,
        @Body request: NetworkAddExpenseRequest
    ): NetworkExpensesResponse

    @PUT("/api/{groupId}/expenses/{expenseId}/")
    suspend fun editExpense(
        @Path("groupId") groupId: Int,
        @Path("expenseId") expenseId: Int,
        @Body request: NetworkAddExpenseRequest
    ): NetworkExpensesResponse

    @DELETE("/api/{groupId}/expenses/{expenseId}/")
    suspend fun deleteExpense(
        @Path("groupId") groupId: Int,
        @Path("expenseId") expenseId: Int,
    ) : Response<Unit>

    @GET("/api/groups/{id}/")
    suspend fun fetchGroupDetails(@Path("id") id: Int): NetworkGroupsResponse

    @GET("/api/{id}/expenses/")
    suspend fun groupExpenses(@Path("id") id: Int): List<NetworkExpensesResponse>

    @GET("/api/{groupId}/expenses/{expenseId}/")
    suspend fun fetchExpenseDetails(
        @Path("groupId") groupId: Int,
        @Path("expenseId") expenseId: Int
    ): NetworkExpensesResponse

    @POST("/api/group-codes/")
    suspend fun groupCode(@Body request: NetworkGroupCodeRequest): NetworkGroupCodeResponse

    @PUT("/api/join/{code}/")
    suspend fun joinToGroup(@Path("code") code: String)

    @GET("/api/icons")
    suspend fun icons(): NetworkIconsResponse

    @GET("/api/friends")
    suspend fun friends(): List<NetworkUser>
}