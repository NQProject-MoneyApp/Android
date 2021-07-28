package com.nqproject.MoneyApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.FragmentActivity
import com.nqproject.MoneyApp.manager.SharedPreferencesManager

class MainActivity : FragmentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        initSingletons()

        setContentView(R.layout.main_activity)
    }

    private fun initSingletons() {
        SharedPreferencesManager.init(this)
    }

}