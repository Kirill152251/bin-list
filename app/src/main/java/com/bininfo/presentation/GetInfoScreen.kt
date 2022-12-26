package com.bininfo.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bininfo.R
import com.bininfo.databinding.FragmentGetInfoScreenBinding
import com.bininfo.domain.getColorFromAttr
import com.bininfo.presentation.view_models.GetInfoScreenEffect
import com.bininfo.presentation.view_models.GetInfoScreenEvent
import com.bininfo.presentation.view_models.GetInfoScreenState
import com.bininfo.presentation.view_models.GetInfoScreenViewModel
import com.google.android.material.R.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.*

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
            buttonRetry.setOnClickListener {
                val bin = editTextBin.text.toString()
                viewModel.setEvent(GetInfoScreenEvent.ValidateInputAndGetBinInfo(bin))
            }
            buttonNavigateToHistory.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, HistoryScreen())
                    .addToBackStack("TAG")
                    .commit()
            }
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
                                textErrorMsg.isVisible = false
                                progressBar.isVisible = false
                            }
                        }
                        GetInfoScreenState.Loading -> {
                            binding.apply {
                                binInfoViews.isVisible = false
                                buttonRetry.isVisible = false
                                textErrorMsg.isVisible = false
                                progressBar.isVisible = true
                            }
                        }
                        is GetInfoScreenState.Error -> {
                            when (state.e) {
                                is HttpException -> {
                                    if (state.e.code() == 404) {
                                        binding.textErrorMsg.text =
                                            getText(R.string.no_data_error_msg)
                                    } else binding.textErrorMsg.text =
                                        getText(R.string.error_server_is_not_response)
                                }
                                is UnknownHostException -> {
                                    binding.textErrorMsg.text =
                                        getText(R.string.no_internet_error_msg)
                                }
                                is NullPointerException -> {
                                    binding.textErrorMsg.text =
                                        getText(R.string.no_data_error_msg)
                                }
                            }
                            binding.apply {
                                binInfoViews.isVisible = false
                                buttonRetry.isVisible = true
                                textErrorMsg.isVisible = true
                                progressBar.isVisible = false
                            }
                        }
                        is GetInfoScreenState.BinInfoState -> {
                            binding.apply {
                                binInfoViews.isVisible = true
                                buttonRetry.isVisible = false
                                textErrorMsg.isVisible = false
                                progressBar.isVisible = false
                                textBrandValue.text = state.binInfo.brand
                                textBankValue.text = state.binInfo.bank
                                textBankSiteValue.text = state.binInfo.bankSite
                                textBankPhoneValue.text = state.binInfo.bankPhone

                                textCountryValue.setOnClickListener {
                                    val uri = String.format(
                                        Locale.ENGLISH,
                                        "geo:%f,%f",
                                        state.binInfo.lat.toFloat(),
                                        state.binInfo.lon.toFloat()
                                    )
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                    startActivity(intent)
                                }
                                val spannableString = SpannableString(state.binInfo.country)
                                spannableString.setSpan(
                                    UnderlineSpan(),
                                    0,
                                    spannableString.length,
                                    0
                                )
                                spannableString.setSpan(
                                    ForegroundColorSpan(requireActivity().getColorFromAttr(attr.colorSecondary)),
                                    0,
                                    spannableString.length,
                                    0
                                )
                                textCountryValue.text = spannableString
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
                    when (effect) {
                        is GetInfoScreenEffect.ShowInputError -> {
                            Toast.makeText(requireContext(), effect.msg, Toast.LENGTH_LONG).show()
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