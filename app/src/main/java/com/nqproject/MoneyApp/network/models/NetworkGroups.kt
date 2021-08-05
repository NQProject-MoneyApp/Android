package com.nqproject.MoneyApp.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NetworkAddGroupRequest(
    val name: String?,
    val icon: Int?,
    val members: List<Int>
)

@Serializable
data class NetworkGroupsResponse(
    val pk: Int?,
    val name: String?,
    val create_date: String?,
    val total_cost: Double?,
    val user_balance: Double?,
    val icon: Int?,
    val members: List<NetworkGroupUsersResponse>,
    @SerialName("non_field_errors")
    val error: String? = null,
)