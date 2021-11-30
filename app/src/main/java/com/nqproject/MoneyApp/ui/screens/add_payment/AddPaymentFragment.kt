package com.nqproject.MoneyApp.ui.screens.add_payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseFragmentArgs
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseScreen
import com.nqproject.MoneyApp.ui.screens.add_expense.AddExpenseViewModel
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class AddPaymentFragment : Fragment() {

    private val args: AddPaymentFragmentArgs by navArgs()
    private val viewModel: AddPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.group)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    AddPaymentScreen(
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