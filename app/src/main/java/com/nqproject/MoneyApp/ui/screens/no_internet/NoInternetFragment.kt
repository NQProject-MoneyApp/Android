package com.nqproject.MoneyApp.ui.screens.group_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.GroupDetailsScreen
import com.nqproject.MoneyApp.ui.screens.NoInternetScreen
import com.nqproject.MoneyApp.ui.screens.expense_list.ExpenseListFragmentDirections
import com.nqproject.MoneyApp.ui.screens.no_internet.NoInternetViewModel
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class NoInternetFragment : Fragment() {

    private val viewModel: NoInternetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    NoInternetScreen {
                        hideNoInternetScreen()
                    }
                }
            }
        }
    }

    private fun hideNoInternetScreen() {
        activity?.onBackPressed()
    }
}