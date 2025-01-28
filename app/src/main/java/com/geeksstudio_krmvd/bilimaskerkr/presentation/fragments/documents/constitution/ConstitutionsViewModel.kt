package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution

import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.constitution.Constitutions
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.ConstitutionsRepository
import com.geeksstudio_krmvd.bilimaskerkr.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstitutionsViewModel @Inject constructor(private val constitutionRepository: ConstitutionsRepository) :
    BaseViewModel() {

    private val _allConstitutionFlow =
        MutableStateFlow<UiState<List<Constitutions>>>(UiState.Loading)

    private val allConstitution = mutableListOf<Constitutions>()

    private val _constitution = MutableStateFlow<UiState<List<Constitutions>>>(UiState.Loading)
    val constitution: Flow<UiState<List<Constitutions>>> = _constitution.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = flow {
        _messageFlow.filterNotNull().collect {
            emit(it)
            _messageFlow.update { null }
        }
    }

    init {
        loadConstitution()
        viewModelScope.launch {
            _allConstitutionFlow.collect { state ->
                if (state is UiState.Success) {
                    allConstitution.clear()
                    allConstitution.addAll(state.data)
                }
                _constitution.update { state }

                if (state is UiState.Error) {
                    _messageFlow.value = Messages.NetworkIsDisconnected
                }
            }
        }
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun loadConstitution() {
        val lawFlow = constitutionRepository.getConstitution()
        lawFlow.collectFlowAsState(_allConstitutionFlow)
    }

    fun onSearchQueryChanged(query: String) {
        val filteredLaws = allConstitution.filter { law ->
            law.section?.contains(query, true) ?: false
        }
        _constitution.update { UiState.Success(mutableListOf()) }
        _constitution.update { UiState.Success(filteredLaws.toMutableList()) }
    }

}