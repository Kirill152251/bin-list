package com.bininfo.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bininfo.R
import com.bininfo.presentation.view_models.HistoryScreenEvent
import com.bininfo.presentation.view_models.HistoryScreenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CleanHistoryAlertDialog : DialogFragment() {

    private val viewModel by viewModels<HistoryScreenViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.alert_dialog_title)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.setEvent(HistoryScreenEvent.DeleteAllHistory)
            }
            .setNegativeButton(R.string.no) { _, _ -> }
            .create()
}