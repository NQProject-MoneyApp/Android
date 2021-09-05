package com.nqproject.MoneyApp.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    ProfileScreen(
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                    )
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.updateUserProfile()
    }
}