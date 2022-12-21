package com.bininfo.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bininfo.data.remote.ApiResult
import com.bininfo.domain.BinInfo
import com.bininfo.domain.GetBinInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetInfoScreenViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GetInfoScreenState>(GetInfoScreenState.Idle)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<GetInfoScreenEvent>()
    private val event = _event.asSharedFlow()

    init {
        handleEvent()
    }

    private fun handleEvent() {
        viewModelScope.launch {
            event.collect { event ->
                when (event) {
                    is GetInfoScreenEvent.GetBinInfo -> binInfo(event.bin)
                    GetInfoScreenEvent.OnRetry -> {}
                }
            }
        }
    }

    private suspend fun binInfo(bin: String) {
        getBinInfoUseCase.getBinInfo(bin).collect { apiResult ->
            when (apiResult) {
                is ApiResult.Error -> {
                    _state.value = GetInfoScreenState.Error
                }
                ApiResult.Loading -> {
                    _state.value = GetInfoScreenState.Loading
                }
                is ApiResult.Success -> {
                    _state.value = GetInfoScreenState.BinInfoState(apiResult.result)
                }
            }
        }
    }

    fun setEvent(event: GetInfoScreenEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

}

sealed class GetInfoScreenState {
    object Idle : GetInfoScreenState()
    object Loading : GetInfoScreenState()
    object Error : GetInfoScreenState()
    data class BinInfoState(val binInfo: BinInfo) : GetInfoScreenState()
}

sealed class GetInfoScreenEvent {
    data class GetBinInfo(val bin: String) : GetInfoScreenEvent()
    object OnRetry : GetInfoScreenEvent()
}