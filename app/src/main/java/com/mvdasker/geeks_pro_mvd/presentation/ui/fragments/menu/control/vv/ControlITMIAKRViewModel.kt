package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv

import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ControlITMIAKRViewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    BaseViewModel() {

    private val _managementState = MutableStateFlow<UiState<List<Governance>>>(UiState.Loading)
    val managementState = _managementState.asStateFlow()

    init {
        repository.fetchConstitutionsVVKr().collectFlowAsState(_managementState)
    }

}