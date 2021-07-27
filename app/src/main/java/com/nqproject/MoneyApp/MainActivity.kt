package com.nqproject.MoneyApp

import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.FragmentActivity
import com.nqproject.MoneyApp.manager.SharedPreferencesManager

class MainActivity : FragmentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSingletons()

        setContentView(R.layout.main_activity)
    }

    private fun initSingletons() {
        SharedPreferencesManager.init(this)
    }

}