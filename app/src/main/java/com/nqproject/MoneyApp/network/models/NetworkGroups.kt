package com.nqproject.MoneyApp.network.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkAddGroupRequest(
    val name: String?
)

data class NetworkGroupsResponse(
    val pk: Int?,
    val name: String?,
    val create_date: Date?,
    @SerializedName("non_field_errors")
    val error: String?,
)
