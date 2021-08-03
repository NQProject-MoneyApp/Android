package com.nqproject.MoneyApp.ui.screens.edit_expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseViewModel
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class EditExpenseFragment : Fragment() {

    private val args: EditExpenseFragmentArgs by navArgs()
    private val viewModel: AddExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.group, args.expenseDetails.participants)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    EditExpenseScreen(
                        expense = args.expenseDetails,
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                        onDeleteExpenseNavigate = {
                            findNavController().popBackStack()
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
    }

}