package com.mvdasker.geeks_pro_mvd.common

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Error(val throwable: Throwable, val message: String = throwable.localizedMessage ?: "Unknown error") : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}