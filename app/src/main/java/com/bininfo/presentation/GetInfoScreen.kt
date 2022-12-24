package com.bininfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bininfo.R
import com.bininfo.databinding.FragmentGetInfoScreenBinding
import com.bininfo.presentation.view_models.GetInfoScreenEffect
import com.bininfo.presentation.view_models.GetInfoScreenEvent
import com.bininfo.presentation.view_models.GetInfoScreenState
import com.bininfo.presentation.view_models.GetInfoScreenViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetInfoScreen : Fragment(R.layout.fragment_get_info_screen) {

    private var _binding: FragmentGetInfoScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GetInfoScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonGetInfo.setOnClickListener {
                val bin = editTextBin.text.toString()
                viewModel.setEvent(GetInfoScreenEvent.ValidateInputAndGetBinInfo(bin))
            }
            //TODO: implement button retry clickListener
            //TODO: implement navigation to history screen button
        }
        collectSideEffect()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        GetInfoScreenState.Idle -> {
                            binding.apply {
                                binInfoViews.isVisible = false
                                buttonRetry.isVisible = false
                                progressBar.isVisible = false
                            }
                        }
                        GetInfoScreenState.Loading -> {
                            binding.apply {
                                binInfoViews.isVisible = false
                                buttonRetry.isVisible = false
                                progressBar.isVisible = true
                            }
                        }
                        GetInfoScreenState.Error -> {
                            binding.apply {
                                binInfoViews.isVisible = false
                                buttonRetry.isVisible = true
                                progressBar.isVisible = false
                            }
                        }
                        is GetInfoScreenState.BinInfoState -> {
                            binding.apply {
                                binInfoViews.isVisible = true
                                buttonRetry.isVisible = false
                                progressBar.isVisible = false
                                textBrandValue.text = state.binInfo.brand
                                textBankValue.text = state.binInfo.bank
                                textBankSiteValue.text = state.binInfo.bankSite
                                textBankPhoneValue.text = state.binInfo.bankPhone
                                textCountryValue.text = state.binInfo.country
                            }
                        }
                    }
                }
            }
        }
    }

    private fun collectSideEffect() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when(effect) {
                        is GetInfoScreenEffect.ShowInputError -> {
                            Snackbar.make(requireView(), effect.msg, 1500).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}