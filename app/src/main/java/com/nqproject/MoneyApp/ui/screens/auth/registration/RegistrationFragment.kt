package com.nqproject.MoneyApp.ui.screens.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    RegistrationScreen(
                        onLoginNavigate = {
                            val action = RegistrationFragmentDirections
                                .actionRegistrationFragmentToLoginFragment()
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }

}