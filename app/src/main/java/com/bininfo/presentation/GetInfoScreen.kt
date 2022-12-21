package com.bininfo.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bininfo.R
import com.bininfo.databinding.FragmentGetInfoScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetInfoScreen : Fragment(R.layout.fragment_get_info_screen) {

    private var _binding: FragmentGetInfoScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}