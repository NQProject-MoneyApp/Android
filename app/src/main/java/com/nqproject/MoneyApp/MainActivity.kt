package com.nqproject.MoneyApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.manager.SharedPreferencesManager
import com.nqproject.MoneyApp.ui.navigation.MainNavigation
import com.nqproject.MoneyApp.ui.screens.auth.login.LoginFragmentDirections
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class MainActivity : FragmentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSingletons()

        setContentView(R.layout.main_activity)



//        setContent {
//            MoneyAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//
//                    MainNavigation()
//                }
//            }
//        }
    }

    private fun initSingletons() {
        SharedPreferencesManager.init(this)
    }

}