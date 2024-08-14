package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlMIAKRViewModel @Inject constructor(private val repository: ManagementsKrRepository) :
    ViewModel() {

    private val _managementState = MutableStateFlow<List<Governance>?>(null)
    val management: StateFlow<List<Governance>?> = _managementState

    init {
        getControlMVD()
    }

    private fun getControlMVD(jobTitle: String? = null) {
        viewModelScope.launch {
            try {
                val result = repository.fetchConstitutionsMIAKr(jobTitle)
                _managementState.value = result
                Log.e("control", "${_managementState.value}")
            } catch (e: Exception) {
                _managementState.value = emptyList()
                Log.e("error", "Exception occurred: ${e.message}")
            }
        }
    }

}