package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlMIAKRViewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    ViewModel() {

    private val _managementState = MutableStateFlow<List<Governance>?>(null)
    val management: Flow<List<Governance>> = _managementState.filterNotNull()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        getControlMVD()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun getControlMVD(jobTitle: String? = null) {
        _messageFlow.value = Messages.ShowProgressBar
        viewModelScope.launch {
            try {
                val result = repository.fetchConstitutionsMIAKr(jobTitle)
                _managementState.value = result
                Log.e("control", "${_managementState.value}")
                _messageFlow.value = Messages.HideProgressBar
            } catch (e: Exception) {
                _managementState.value = emptyList()
                Log.e("error", "Exception occurred: ${e.message}")
                _messageFlow.value = Messages.NetworkIsDisconnected
                _messageFlow.value = Messages.HideProgressBar
            }
        }
    }

}