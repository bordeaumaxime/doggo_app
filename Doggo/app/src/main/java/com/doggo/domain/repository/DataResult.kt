package com.doggo.domain.repository

/**
 * Generic data class returned by repositories for requests to the API
 */
sealed class DataResult<out T: Any> {
    data class Error(val errorType: ErrorType) : DataResult<Nothing>()
    data class Success<out T : Any>(val data: T) : DataResult<T>()

    enum class ErrorType {
        // there could be more error types, but for now this is good enough
        NETWORK, UNKNOWN
    }
}