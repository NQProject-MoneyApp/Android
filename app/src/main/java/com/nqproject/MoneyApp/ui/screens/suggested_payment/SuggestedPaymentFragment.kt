package com.nqproject.MoneyApp.ui.screens.suggested_payment

import SuggestedPaymentViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nqproject.MoneyApp.ui.theme.MoneyAppTheme
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

class SuggestedPaymentFragment : Fragment() {

    private val args: SuggestedPaymentFragmentArgs by navArgs()
    private val viewModel: SuggestedPaymentViewModel by viewModels()

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
                    SuggestedPaymentScreen(
                        onBackNavigate = {
                            requireActivity().onBackPressed()
                        },
                        onNewPaymentNavigate = {
                            val action = SuggestedPaymentFragmentDirections
                                .actionSuggestedPaymentToAddPaymentFragment(args.group)
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }
}