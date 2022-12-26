package com.bininfo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bininfo.R
import com.bininfo.databinding.FragmentHistoryScreenBinding
import com.bininfo.presentation.view_models.HistoryScreenEvent
import com.bininfo.presentation.view_models.HistoryScreenState
import com.bininfo.presentation.view_models.HistoryScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryScreen : Fragment(R.layout.fragment_history_screen) {

    private var _binding: FragmentHistoryScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HistoryScreenViewModel>()

    private val adapter = BinInfoAdapter {
        viewModel.setEvent(HistoryScreenEvent.DeleteItem(it))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.setEvent(HistoryScreenEvent.FetchHistory)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is HistoryScreenState.BinInfoHistoryState -> {
                            binding.textNoHistory.isVisible = false
                            state.binInfo.collect {
                                adapter.submitList(it)
                            }
                        }
                        HistoryScreenState.EmptyHistory -> {
                            binding.textNoHistory.isVisible = true
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