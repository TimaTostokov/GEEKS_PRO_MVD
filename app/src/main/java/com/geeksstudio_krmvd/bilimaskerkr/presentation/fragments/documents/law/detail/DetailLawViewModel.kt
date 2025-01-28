package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.law.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.law.LawsChapter
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.LawRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailLawViewModel @Inject constructor(
    private val repository: LawRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _lawsDetail = MutableStateFlow<UiState<LawsChapter?>>(UiState.Loading)
    val lawsDetail: StateFlow<UiState<LawsChapter?>> = _lawsDetail.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private val id = savedStateHandle.get<Int>(LAW_LAY)

    fun clearMessage() {
        _messageFlow.value = null
    }

    fun setId(id: Int) {
        savedStateHandle[LAW_LAY] = id
    }

    init {
        getLawsDetail()
    }

    private fun getLawsDetail() {
        viewModelScope.launch {
            try {
                id?.let { lawsId ->
                    repository.getLawById(lawsId).fold(
                        onSuccess = { lawsDetail ->
                            _lawsDetail.value = UiState.Success(lawsDetail)
                        },
                        onFailure = { error ->
                            _lawsDetail.value = UiState.Error(error, error.message ?: "unknown error!")
                            _messageFlow.value = Messages.NetworkIsDisconnected
                            Log.e("toli", "во viewModel не пришли данные")
                        }
                    )
                }
            }catch (e: Exception) {
                _messageFlow.value = Messages.NetworkIsDisconnected
            }
        }
    }

    companion object {
        const val LAW_LAY = "id"
    }

}