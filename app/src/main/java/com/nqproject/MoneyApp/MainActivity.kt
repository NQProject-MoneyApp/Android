package com.nqproject.MoneyApp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.network.MoneyAppClient

class MainActivity : FragmentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(R.layout.main_activity)
    }

    override fun onStart() {
        super.onStart()
        MoneyAppClient.logoutCallback = {
            runOnUiThread {
                AuthenticationManager.token = null
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_loginFragment)
                Toast.makeText(this@MainActivity, "Logged out", Toast.LENGTH_LONG)
                    .show()
            }
        }

        MoneyAppClient.noInternetCallback = {
            runOnUiThread {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_no_internet)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        MoneyAppClient.logoutCallback = null
        MoneyAppClient.noInternetCallback = null
    }
}