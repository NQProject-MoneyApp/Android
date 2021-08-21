package com.nqproject.MoneyApp.ui.screens.edit_group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.nqproject.MoneyApp.ui.screens.add_group.AddGroupViewModel
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme

class EditGroupFragment: Fragment() {

    private val args: EditGroupFragmentArgs by navArgs()
    private val viewModel: AddGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoneyAppTheme {
                    EditGroupScreen(
                        group = args.group,
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        }
                        )
                    }
                }
            }
        }
}