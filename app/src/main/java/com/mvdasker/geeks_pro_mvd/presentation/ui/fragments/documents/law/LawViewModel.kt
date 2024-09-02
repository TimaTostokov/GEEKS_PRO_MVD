package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LawViewModel @Inject constructor(private val lawRepository: LawRepository) : BaseViewModel() {

    private val _law: MutableStateFlow<UiState<List<Law>>> = MutableStateFlow(UiState.Loading)
    val law: Flow<UiState<List<Law>>> = _law.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        loadLaw()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun loadLaw() {
        viewModelScope.launch(Dispatchers.IO) {
            val lawFlow = lawRepository.getLaw()
            lawFlow.collectFlowAsState(_law)
        }
    }
}