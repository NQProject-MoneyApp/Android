package com.nqproject.MoneyApp.ui.screens.group_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nqproject.MoneyApp.R
import com.nqproject.MoneyApp.ui.screens.GroupListScreen
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme
import androidx.fragment.app.viewModels

class GroupListFragment : Fragment() {

    private val viewModel: GroupsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    GroupListScreen(
                        onAddGroupNavigate = {
                            val action = GroupListFragmentDirections
                                .actionGroupListFragmentToAddGroupFragment()
                            findNavController().navigate(action)
                        },
                        onGroupDetailsNavigate = { group ->
                            val action = GroupListFragmentDirections
                                .actionGroupListFragmentToGroupDetailsFragment(group)
                            findNavController().navigate(action)
                        },
                        onLoginNavigate = {
                            findNavController().navigate(R.id.action_global_loginFragment)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateGroups(withLoader = false)
    }
}