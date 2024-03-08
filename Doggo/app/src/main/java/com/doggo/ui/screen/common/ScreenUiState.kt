package com.doggo.ui.screen.common

import com.doggo.domain.repository.DataResult

sealed class ScreenUiState<out T : Any> {
    data object Loading : ScreenUiState<Nothing>()

    data class Result<out T : Any>(val data: T) : ScreenUiState<T>()

    data class Error(val type: Type) : ScreenUiState<Nothing>() {
        enum class Type {
            NETWORK, UNKNOWN
        }
    }
}

fun <T : Any> DataResult<T>.toScreenUiState() = when (this) {
    is DataResult.Error -> ScreenUiState.Error(this.toUiErrorType())
    is DataResult.Success -> ScreenUiState.Result(this.data)
}

fun DataResult.Error.toUiErrorType() = when (errorType) {
    DataResult.ErrorType.NETWORK -> ScreenUiState.Error.Type.NETWORK
    DataResult.ErrorType.UNKNOWN -> ScreenUiState.Error.Type.UNKNOWN
}