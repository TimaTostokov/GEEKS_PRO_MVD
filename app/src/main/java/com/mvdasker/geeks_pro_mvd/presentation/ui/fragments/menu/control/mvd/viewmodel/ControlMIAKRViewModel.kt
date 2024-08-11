package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.viewmodel

import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ControlMIAKRViewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    BaseViewModel() {

    private val _managementState = MutableStateFlow<UiState<List<Governance>>>(UiState.Loading)
    val managementState = _managementState.asStateFlow()

    init {
        repository.fetchConstitutionsVVKr().collectFlowAsState(state = _managementState)
    }
}