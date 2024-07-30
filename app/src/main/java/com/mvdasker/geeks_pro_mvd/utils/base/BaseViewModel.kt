package com.mvdasker.geeks_pro_mvd.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected open fun <T> getData(
        state: MutableStateFlow<UiState<T>>,
        useCaseFlow: Flow<T>
    ) {
        state.value = UiState.Loading
        viewModelScope.launch {
            useCaseFlow
                .catch { e -> state.value = UiState.Error(e, "") }
                .collect { result ->
                    state.value = UiState.Success(result)
                }
        }
    }
}