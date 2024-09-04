package com.mvdasker.geeks_pro_mvd.common

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Error(val exception: Throwable, val message: String = exception.localizedMessage ?: "Unknown error") : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}