package com.doggo.ui.screen.common

sealed class ScreenUiState<out T : Any> {
    data object Loading : ScreenUiState<Nothing>()

    data class Result<out T : Any>(val data: T) : ScreenUiState<T>()

    data class Error(val type: Type) : ScreenUiState<Nothing>() {
        enum class Type {
            NETWORK, UNKNOWN
        }
    }
}