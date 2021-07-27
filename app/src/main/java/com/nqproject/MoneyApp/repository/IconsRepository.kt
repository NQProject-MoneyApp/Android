package com.nqproject.MoneyApp.repository

import com.nqproject.MoneyApp.network.MoneyAppClient
import com.nqproject.MoneyApp.network.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IconsRepository {

    suspend fun icons(): SimpleResult<List<MoneyAppIcon>> {
        val result = withContext(Dispatchers.IO) {
            MoneyAppClient.icons()
        }

        return when(result) {
            is SimpleResult.Error -> SimpleResult.Error(result.error)
            is SimpleResult.Success -> SimpleResult.Success(
                result.data.map { MoneyAppIcon.from(it) })
        }
    }

}