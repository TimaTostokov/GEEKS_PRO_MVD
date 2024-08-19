package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.kg

import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import com.mvdasker.geeks_pro_mvd.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class ControlKgVIewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    BaseViewModel() {

    private val _managementState = MutableStateFlow<UiState<List<Governance>>>(UiState.Loading)
    val managementState = _managementState.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        repository.fetchConstitutionsKr().collectFlowAsState(_managementState)
        _messageFlow.value = Messages.NetworkIsDisconnected
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

}