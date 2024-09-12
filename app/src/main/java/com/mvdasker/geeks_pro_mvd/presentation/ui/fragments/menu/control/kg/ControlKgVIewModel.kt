package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.kg

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlKgVIewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    BaseViewModel() {

    private val _managementState = MutableStateFlow<UiState<List<Governance>>>(UiState.Loading)
    val managementState = _managementState.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        getControlKR()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun getControlKR() {
        _messageFlow.value = Messages.ShowProgressBar
        viewModelScope.launch {
            try {
                val result = repository.fetchConstitutionsKr()
                _managementState.value = UiState.Success(result)
                Log.e("control", "${_managementState.value}")
                _messageFlow.value = Messages.HideProgressBar
            } catch (t: Throwable) {
                _managementState.value = UiState.Error(throwable = t, message = "Loading error")
                Log.e("error", "Exception occurred: ${t.message}")
                _messageFlow.value = Messages.NetworkIsDisconnected
                _messageFlow.value = Messages.HideProgressBar
            }
        }
    }

}