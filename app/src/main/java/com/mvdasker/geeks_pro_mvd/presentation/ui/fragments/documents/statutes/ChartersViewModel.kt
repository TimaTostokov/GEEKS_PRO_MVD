package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.statutes

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.data.repositories.CharterRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartersViewModel @Inject constructor(private val charterRepository: CharterRepository) :
    BaseViewModel() {

    private val _charters: MutableStateFlow<UiState<List<Charter>>> =
        MutableStateFlow(UiState.Loading)
    val charters: Flow<UiState<List<Charter>>> = _charters.asStateFlow()

    init {
        loadChartersList()
    }

    private fun loadChartersList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = charterRepository.getListCharters()
                _charters.value = UiState.Success(result)
            } catch (t: Throwable) {
                _charters.value = UiState.Error(throwable = t, message = "")
            }
        }
    }
}