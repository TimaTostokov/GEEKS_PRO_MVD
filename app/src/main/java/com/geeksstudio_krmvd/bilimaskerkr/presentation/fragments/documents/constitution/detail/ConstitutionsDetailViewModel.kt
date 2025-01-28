package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.constitution.ConstitutionsChapter
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.ConstitutionsRepository
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.law.detail.DetailLawViewModel.Companion.LAW_LAY
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.library.detail.DetailViewModel.Companion.LIBRARY_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstitutionsDetailViewModel @Inject constructor(
    private val repository: ConstitutionsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _constitutionsDetail =
        MutableStateFlow<UiState<ConstitutionsChapter?>>(UiState.Loading)
    val constitutionsDetail: StateFlow<UiState<ConstitutionsChapter?>> = _constitutionsDetail

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private val id = savedStateHandle.get<Int>(LIBRARY_ID_KEY)

    fun clearMessage() {
        _messageFlow.value = null
    }

    fun setId(id: Int) {
        savedStateHandle[LAW_LAY] = id
    }

    init {
        getConstitutionsDetail()
    }

    private fun getConstitutionsDetail() {
        viewModelScope.launch {
            try {
                id?.let { constitutionId ->
                    repository.getConstitutionById(constitutionId).fold(
                        onSuccess = { constitutionDetail ->
                            _constitutionsDetail.value = UiState.Success(constitutionDetail)
                        },
                        onFailure = { error ->
                            _constitutionsDetail.value =
                                UiState.Error(error, error.message ?: "unknown error!")
                            _messageFlow.value = Messages.NetworkIsDisconnected
                        }
                    )
                }
            }catch (e: Exception) {
                _messageFlow.value = Messages.NetworkIsDisconnected
            }
        }
    }

}