package com.nqproject.MoneyApp.ui.screens.expense_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.LoginScreen
import com.nqproject.MoneyApp.ui.screens.auth.login.LoginFragmentDirections
import com.nqproject.MoneyApp.ui.screens.expense_list.ExpenseListFragmentArgs
import com.nqproject.MoneyApp.ui.screens.expense_list.ExpenseListScreen
import com.nqproject.MoneyApp.ui.screens.group_details.GroupDetailsFragmentArgs
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme


class ExpenseDetailsFragment : Fragment() {

    private val args: ExpenseDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    ExpenseDetailsScreen(
                        expense = args.expense,
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        }
                    )
                }
            }
        }
    }

}