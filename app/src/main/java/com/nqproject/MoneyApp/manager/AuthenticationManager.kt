package com.nqproject.MoneyApp.manager

object AuthenticationManager {

    private const val TOKEN_KEY = "MoneyAppPreferences.TOKEN_KEY"

    var token: String?
        get() = SharedPreferencesManager.read(TOKEN_KEY)
        set(value) {
            SharedPreferencesManager.write(TOKEN_KEY, if (value == null) null else "Token $value")
        }
}