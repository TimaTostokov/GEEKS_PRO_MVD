package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryResponse
import com.mvdasker.geeks_pro_mvd.data.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: HistoryRepository)
    : ViewModel(){

    private val _history: MutableStateFlow<UiState<HistoryResponse?>> = MutableStateFlow(UiState.Loading)
    val history: Flow<UiState<HistoryResponse?>> = _history.asStateFlow()

    fun fetchHistory(pk: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getHistory(pk)
                _history.value = UiState.Success(result)
            } catch (t: Throwable) {
                _history.value = UiState.Error(throwable = t, message = "Loading error")
            }
        }
    }
}