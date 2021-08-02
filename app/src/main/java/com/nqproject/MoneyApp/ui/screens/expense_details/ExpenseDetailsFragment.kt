package com.nqproject.MoneyApp.ui.screens.expense_details

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


class ExpenseDetailsFragment : Fragment() {

    private val args: ExpenseDetailsFragmentArgs by navArgs()
    private val viewModel: ExpenseDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.expense)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    ExpenseDetailsScreen(
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                        onEditExpenseNavigate = {
                            if(viewModel.expenseDetails.value == null) {
                                // TODO: do nothing and return
                            }

                            val action = ExpenseDetailsFragmentDirections
                                .actionExpenseDetailsFragmentToEditExpenseFragment(
                                    viewModel.expenseDetails.value!!
                                )
                            findNavController().navigate(action)
                        },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateExpense()
    }
}