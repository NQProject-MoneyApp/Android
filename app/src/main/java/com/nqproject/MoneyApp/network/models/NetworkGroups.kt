package com.nqproject.MoneyApp.network.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkAddGroupRequest(
    val name: String?,
    val icon: Int?
)

data class NetworkGroupsResponse(
    val pk: Int?,
    val name: String?,
    val create_date: Date?,
    val total_cost: Double?,
    val user_balance: Double?,
    val icon: Int?,
    @SerializedName("non_field_errors")
    val error: String?,
)
