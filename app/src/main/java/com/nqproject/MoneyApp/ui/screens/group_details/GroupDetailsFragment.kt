package com.nqproject.MoneyApp.ui.screens.group_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.GroupDetailsScreen
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class GroupDetailsFragment : Fragment() {

    private val args: GroupDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    GroupDetailsScreen(
                        group = args.group,
                        onExpensesListNavigate = {
                            val action = GroupDetailsFragmentDirections
                                .actionGroupDetailsFragmentToExpenseListFragment(args.group)
                            findNavController().navigate(action)
                        },
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                    )
                }
            }
        }
    }

}