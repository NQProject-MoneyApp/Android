package com.nqproject.MoneyApp.ui.screens.group_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.nqproject.MoneyApp.ui.screens.GroupListScreen
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class GroupListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    GroupListScreen()
                }
            }
        }
    }

}