package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.history.HistoryModel
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: HistoryRepository) :
    ViewModel() {

    private val _history: MutableStateFlow<UiState<HistoryModel?>> =
        MutableStateFlow(UiState.Loading)
    val history: StateFlow<UiState<HistoryModel?>> = _history.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    fun clearMessage() {
        _messageFlow.value = null
    }

    fun fetchHistory(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getHistory(slug)
                _history.value = UiState.Success(result)
            } catch (t: Throwable) {
                _messageFlow.value = Messages.NetworkIsDisconnected
                _history.value = UiState.Error(throwable = t, message = "Loading error")
            }
        }
    }

}