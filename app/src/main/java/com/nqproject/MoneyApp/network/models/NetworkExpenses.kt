package com.nqproject.MoneyApp.network.models

import com.nqproject.MoneyApp.repository.MoneyAppIcon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NetworkExpensesRequest(
    val name: String,
    val amount: Float,
    val participants: List<Int>
)

@Serializable
data class NetworkExpensesResponse(
    val pk: Int,
    @SerialName("group_id")
    val groupId: Int,
    val name: String,
    val author: NetworkUser,
    val paid_by: String,
    val payment_to: String?,
    val type: String,
    val amount: Double,
    @SerialName("create_date")
    val createDate: String,
    val participants: List<NetworkUser>
)

enum class ExpenseType(val type: String) {
    Payment("payment"),
    Expense("expense");

    companion object {
        fun from(type: String): ExpenseType {
            return ExpenseType.values().firstOrNull { it.type == type} ?: ExpenseType.Expense
        }
    }
}
