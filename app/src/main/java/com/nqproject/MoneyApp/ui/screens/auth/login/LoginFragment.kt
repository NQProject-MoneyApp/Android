package com.nqproject.MoneyApp.ui.screens.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nqproject.MoneyApp.ui.screens.LoginScreen
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    LoginScreen(
                        onRegisterNavigate = {
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
                            findNavController().navigate(action)
                        },
                        onLoggedInNavigate = {
                            val action = LoginFragmentDirections
                                .actionLoginFragmentToGroupListFragment()
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }

}