package com.nqproject.MoneyApp.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    Text("Loading...")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if(AuthenticationManager.token != null) {
            val action = SplashFragmentDirections.actionSplashFragmentToGroupListFragment()
            findNavController().navigate(action)
        }
        else {
            val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

}