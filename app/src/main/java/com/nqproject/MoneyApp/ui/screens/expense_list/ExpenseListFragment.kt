package com.nqproject.MoneyApp.ui.screens.expense_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme


class ExpenseListFragment : Fragment() {

    private val args: ExpenseListFragmentArgs by navArgs()
    private val viewModel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.init(args.group.id)
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    ExpenseListScreen(
                        onAddExpenseNavigate = {
                            val action =
                                ExpenseListFragmentDirections
                                    .actionExpenseListFragmentToAddExpenseFragment(
                                        args.group
                                    )
                            findNavController().navigate(action)
                        },
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                        onExpenseDetailsNavigate = { expense ->
                            val action =
                                ExpenseListFragmentDirections
                                    .actionExpenseListFragmentToExpenseDetailsFragment(expense, args.group)
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateExpenses(withLoader = false)
    }
}