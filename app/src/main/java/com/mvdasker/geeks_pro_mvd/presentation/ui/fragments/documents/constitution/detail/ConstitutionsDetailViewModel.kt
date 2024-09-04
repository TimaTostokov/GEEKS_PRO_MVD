package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Either
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.ConstitutionsChapter
import com.mvdasker.geeks_pro_mvd.data.repositories.ConstitutionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstitutionsDetailViewModel @Inject constructor(
    private val repository: ConstitutionsRepository
) : ViewModel() {

    private val _constitutionDetail = MutableStateFlow<UiState<ConstitutionsChapter>>(UiState.Loading)
    val constitutionDetail: StateFlow<UiState<ConstitutionsChapter>> = _constitutionDetail

    fun loadConstitutionById(id: Int) {
        viewModelScope.launch {
            repository.getConstitutionById(id)
                .collect { result ->
                    _constitutionDetail.value = when (result){
                        is Either.Left -> UiState.Error(result.left!!, result.left.message ?: "Unknown error")
                        is Either.Right -> UiState.Success(result.right!!)
                    }
                }
        }
    }
}
