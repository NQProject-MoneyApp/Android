package com.nqproject.MoneyApp.manager

import android.app.Application

class MoneyAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPreferencesManager.init(this)
    }

}