package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv.search_vv

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
class SearchControlVVViewModel @Inject constructor(
    private val repository: ManagementsKrRepository
) : ViewModel() {

    private val _controlITMIAKR = MutableStateFlow<List<Governance>?>(null)
    val controlITMIAKR: StateFlow<List<Governance>?> = _controlITMIAKR

    init {
        controlITMIAKR()
    }

    private fun controlITMIAKR(jobTittle: String? = null) {
        viewModelScope.launch {
            try {
                val result = repository.fetchConstitutionsMIAKr(jobTittle)
                _controlITMIAKR.value = result
                Log.e("controls", "${controlITMIAKR.value}")
            } catch (e: Exception) {
                _controlITMIAKR.value = emptyList()
                Log.e("error", "Exception occurred: ${e.message}")
            }
        }
    }

}