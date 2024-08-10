package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.remote.model.Law
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LawViewModel @Inject constructor(private val lawRepository: LawRepository) : ViewModel() {

    private val _law: MutableStateFlow<UiState<List<Law>>> = MutableStateFlow(UiState.Loading)
    val law: Flow<UiState<List<Law>>> = _law.asStateFlow()

    init {
        loadLaw()
    }

    private fun loadLaw() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = lawRepository.getLaw()
                _law.value = UiState.Success(result)
            } catch (t: Throwable) {
                _law.value = UiState.Error(throwable = t, message = "loading error")
            }
        }
    }
}