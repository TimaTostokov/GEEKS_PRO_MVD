package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.charters

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.data.repositories.CharterRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartersViewModel @Inject constructor(private val charterRepository: CharterRepository) :
    BaseViewModel() {

    private val _charters: MutableStateFlow<UiState<List<Charter>>> =
        MutableStateFlow(UiState.Loading)
    val charters: Flow<UiState<List<Charter>>> = _charters.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        loadChartersList()
        viewModelScope.launch {
            _charters.collect { state ->
                if (state is UiState.Error) {
                    _messageFlow.value = Messages.NetworkIsDisconnected
                }
            }
        }
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun loadChartersList() {
        try {
            charterRepository.getListChartersFlow().collectFlowAsState(_charters)
        } catch (e: Exception) {
            _messageFlow.value = Messages.NetworkIsDisconnected
        }
    }

}