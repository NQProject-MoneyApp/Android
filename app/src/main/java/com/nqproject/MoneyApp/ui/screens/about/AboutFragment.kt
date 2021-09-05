package com.nqproject.MoneyApp.ui.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    AboutScreen(
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                    )
                }
            }
        }
    }
}