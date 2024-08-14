package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.search_mvd

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
class SearchControlViewModel @Inject constructor(
    private val repository: ManagementsKrRepository
) : ViewModel() {

    private val _controlIMIAKRA = MutableStateFlow<List<Governance>?>(null)
    val controlIMIAKRA: StateFlow<List<Governance>?> = _controlIMIAKRA

    init {
        controlIMIAKRA()
    }

    private fun controlIMIAKRA( jobTittle: String? = null) {
        viewModelScope.launch {
            try {
                val result = repository.fetchConstitutionsMIAKr(jobTittle)
                _controlIMIAKRA.value = result
                Log.e("controls", "${controlIMIAKRA.value}")
            } catch (e: Exception) {
                _controlIMIAKRA.value = emptyList()
                Log.e("error", "Exception occurred: ${e.message}")
            }
        }
    }

}