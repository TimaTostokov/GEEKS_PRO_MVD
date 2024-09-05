package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.Constitutions
import com.mvdasker.geeks_pro_mvd.data.repositories.ConstitutionsRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstitutionsViewModel @Inject constructor(private val constitutionRepository: ConstitutionsRepository) : BaseViewModel() {

    private val _constitution: MutableStateFlow<UiState<List<Constitutions>>> = MutableStateFlow(UiState.Loading)
    val constitution: Flow<UiState<List<Constitutions>>> = _constitution.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        loadConstitution()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun loadConstitution() {
        viewModelScope.launch() {
            val constitutionFlow = constitutionRepository.getConstitution()
            constitutionFlow.collectFlowAsState(_constitution)
        }
    }
}