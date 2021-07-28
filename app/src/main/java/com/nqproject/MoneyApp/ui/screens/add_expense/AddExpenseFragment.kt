package com.nqproject.MoneyApp.ui.screens.add_expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.expense_list.ExpenseListFragmentArgs
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class AddExpenseFragment : Fragment() {

    private val args: AddExpenseFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    AddExpenseScreen(
                        groupId = args.group.id,
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        }
                    )
                }
            }
        }
    }

}