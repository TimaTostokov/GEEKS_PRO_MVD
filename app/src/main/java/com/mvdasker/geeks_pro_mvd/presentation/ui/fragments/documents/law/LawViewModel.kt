package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
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
class LawViewModel @Inject constructor(private val lawRepository: LawRepository) : BaseViewModel() {

    private val _allLawFlow = MutableStateFlow<UiState<List<Law>>>(UiState.Loading)
    private val allLaws = mutableListOf<Law>()
    private val _law = MutableStateFlow<UiState<List<Law>>>(UiState.Loading)
    val law: Flow<UiState<List<Law>>> = _law.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = flow {
        _messageFlow.filterNotNull().collect {
            emit(it)
            _messageFlow.update { null }
        }
    }

    init {
        loadLaw()
        viewModelScope.launch {
            _allLawFlow.collect { state ->
                if (state is UiState.Success) {
                    allLaws.clear()
                    allLaws.addAll(state.data)
                }
                _law.update { state }
            }
        }
    }

    private fun loadLaw() {
        val lawFlow = lawRepository.getLaw()
        lawFlow.collectFlowAsState(_allLawFlow)
    }

    fun onSearchQueryChanged(query: String) {
        val filteredLaws = allLaws.filter { law ->
            law.section?.contains(query, true) ?: false
        }
        _law.update { UiState.Success(mutableListOf()) }
        _law.update { UiState.Success(filteredLaws.toMutableList()) }
    }

}