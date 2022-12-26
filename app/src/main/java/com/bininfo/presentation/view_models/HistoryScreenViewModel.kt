package com.bininfo.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bininfo.domain.BinInfoHistory
import com.bininfo.domain.BinInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val repository: BinInfoRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Idle)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HistoryScreenEvent>()
    private val event = _event.asSharedFlow()

    init {
        handleEvent()
    }

    private fun handleEvent() {
        viewModelScope.launch {
            event.collect { event ->
                when (event) {
                    HistoryScreenEvent.FetchHistory -> {
                        val historyFlow = repository.getBinHistory()
                        _state.value = HistoryScreenState.BinInfoHistoryState(historyFlow)
                    }
                    HistoryScreenEvent.DeleteAllHistory -> {
                        withContext(Dispatchers.IO) {
                            repository.clearHistory()
                        }
                    }
                    is HistoryScreenEvent.DeleteItem -> {
                        withContext(Dispatchers.IO) {
                            repository.deleteBinInfoFromDb(event.binInfoHistory)
                        }
                    }
                }
            }
        }
    }

    fun setEvent(event: HistoryScreenEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }
}

sealed class HistoryScreenState {
    object Idle : HistoryScreenState()
    data class BinInfoHistoryState(val binInfo: Flow<List<BinInfoHistory>>) : HistoryScreenState()
}

sealed class HistoryScreenEvent {
    object FetchHistory : HistoryScreenEvent()
    data class DeleteItem(val binInfoHistory: BinInfoHistory) : HistoryScreenEvent()
    object DeleteAllHistory : HistoryScreenEvent()
}
