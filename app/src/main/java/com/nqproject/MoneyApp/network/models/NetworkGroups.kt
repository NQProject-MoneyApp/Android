package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NetworkAddGroupRequest(
    val name: String? = null,
    val icon: Int? = null,
    val members: List<Int>? = null,
    @SerialName("is_favourite")
    val isFavourite: Boolean? = null
)

@Serializable
data class NetworkGroupsResponse(
    val pk: Int?,
    val name: String?,
    @SerialName("create_date")
    val createDate: String?,
    @SerialName("total_cost")
    val totalCost: Double?,
    @SerialName("user_balance")
    val userBalance: Double?,
    val icon: Int?,
    @SerialName("is_favourite")
    val isFavourite: Boolean?,
    val members: List<NetworkGroupUsersResponse>,
    @SerialName("non_field_errors")
    val error: String? = null,
)