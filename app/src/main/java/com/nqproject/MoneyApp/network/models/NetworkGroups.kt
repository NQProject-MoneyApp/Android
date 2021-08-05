package com.nqproject.MoneyApp.network.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkAddGroupRequest(
    val name: String? = null,
    val icon: Int? = null,
    val members: List<Int>? = null,
    @SerializedName("is_favourite")
    val isFavourite: Boolean? = null
)

data class NetworkGroupsResponse(
    val pk: Int?,
    val name: String?,
    val create_date: Date?,
    val total_cost: Double?,
    val user_balance: Double?,
    val icon: Int?,
    @SerializedName("is_favourite")
    val isFavourite: Boolean?,
    val members: List<NetworkGroupUsersResponse>,
    @SerializedName("non_field_errors")
    val error: String?,
)
