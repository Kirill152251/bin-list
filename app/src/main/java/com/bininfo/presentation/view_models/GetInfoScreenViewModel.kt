package com.bininfo.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bininfo.R
import com.bininfo.data.remote.ApiResult
import com.bininfo.domain.BinInfo
import com.bininfo.domain.GetBinInfoUseCase
import com.bininfo.domain.InputValidationUseCase
import com.bininfo.domain.InputValidationUseCase.InputError.*
import com.bininfo.domain.ManageResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetInfoScreenViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase,
    private val inputValidationUseCase: InputValidationUseCase,
    private val stringResource: ManageResources
) : ViewModel() {

    private val _state = MutableStateFlow<GetInfoScreenState>(GetInfoScreenState.Idle)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<GetInfoScreenEvent>()
    private val event = _event.asSharedFlow()

    private val _effect = Channel<GetInfoScreenEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleEvent()
    }

    private fun handleEvent() {
        viewModelScope.launch {
            event.collect { event ->
                when (event) {
                    GetInfoScreenEvent.OnRetry -> {}
                    is GetInfoScreenEvent.ValidateInputAndGetBinInfo -> {
                        validateAndGetBinInfo(event.bin)
                    }
                }
            }
        }
    }

    private suspend fun validateAndGetBinInfo(bin: String) {
        when (inputValidationUseCase.isValid(bin)) {
            NO_ERRORS -> getBinInfo(bin)
            NOT_EIGHT_DIGITS -> setEffect {
                GetInfoScreenEffect.ShowInputError(
                    stringResource.string(
                        R.string.input_error_length
                    )
                )
            }
            INVALID_CHARS -> setEffect {
                GetInfoScreenEffect.ShowInputError(
                    stringResource.string(R.string.input_error_invalid_chars)
                )
            }
            EMPTY -> setEffect {
                GetInfoScreenEffect.ShowInputError(
                    stringResource.string(R.string.input_error_empty)
                )
            }
        }
    }

    private suspend fun getBinInfo(bin: String) {
        getBinInfoUseCase.getBinInfo(bin).collect { apiResult ->
            when (apiResult) {
                is ApiResult.Error -> {
                    _state.value = GetInfoScreenState.Error(apiResult.e)
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

    private fun setEffect(builder: () -> GetInfoScreenEffect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

}

sealed class GetInfoScreenState {
    object Idle : GetInfoScreenState()
    object Loading : GetInfoScreenState()
    data class Error(val e: Exception) : GetInfoScreenState()
    data class BinInfoState(val binInfo: BinInfo) : GetInfoScreenState()
}

sealed class GetInfoScreenEvent {
    data class ValidateInputAndGetBinInfo(val bin: String) : GetInfoScreenEvent()
    object OnRetry : GetInfoScreenEvent()
}

sealed class GetInfoScreenEffect {

    data class ShowInputError(val msg: String) : GetInfoScreenEffect()
}