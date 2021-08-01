package com.nqproject.MoneyApp.ui.screens.edit_expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class EditExpenseFragment : Fragment() {

    private val args: EditExpenseFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    EditExpenseScreen(
                        groupId = args.group.id,
                        expense = args.expenseDetails,
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        }
                    )
                }
            }
        }
    }

}