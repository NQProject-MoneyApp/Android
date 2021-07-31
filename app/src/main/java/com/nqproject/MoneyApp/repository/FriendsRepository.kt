package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FriendsRepository {

    suspend fun fetchFriends(): SimpleResult<List<User>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.friends()
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map { User(
                    pk = it.pk!!,
                    name = it.username!!,
                    email = it.email!!,
                    balance = 0.0,
                )} )
        }
    }
}