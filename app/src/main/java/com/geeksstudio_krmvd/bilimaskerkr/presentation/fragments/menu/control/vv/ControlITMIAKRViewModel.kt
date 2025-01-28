package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.control.vv

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.mangements.Governance
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.ManagementsKrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlITMIAKRViewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    ViewModel() {

    private val _managementStateVv = MutableStateFlow<List<Governance>?>(null)
    val managementVv: Flow<List<Governance>?> = _managementStateVv.filterNotNull()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        getControlVV()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun getControlVV(jobTitle: String? = null) {
        _messageFlow.value = Messages.ShowProgressBar
        viewModelScope.launch {
            try {
                val result = repository.fetchConstitutionsVVKr(jobTitle)
                _managementStateVv.value = result
                Log.e("control", "${_managementStateVv.value}")
                _messageFlow.value = Messages.HideProgressBar
            } catch (e: Exception) {
                _managementStateVv.value = emptyList()
                Log.e("error", "Exception occurred: ${e.message}")
                _messageFlow.value = Messages.NetworkIsDisconnected
                _messageFlow.value = Messages.HideProgressBar
            }
        }
    }

}