package com.nqproject.MoneyApp.manager

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private lateinit var sharedPreferences: SharedPreferences
    private const val PREFERENCES_NAME = "MoneyAppPreferences"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun write(key: String, value: String?) {
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }
}