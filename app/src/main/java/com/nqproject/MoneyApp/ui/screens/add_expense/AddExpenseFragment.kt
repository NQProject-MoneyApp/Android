package com.nqproject.MoneyApp.ui.screens.add_expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class AddExpenseFragment : Fragment() {

    private val args: AddExpenseFragmentArgs by navArgs()
    private val viewModel: AddExpenseViewModel by viewModels()

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