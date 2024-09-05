package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.detail

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Either
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.LawsCharter
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail.DetailViewModel.Companion.LIBRARY_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailLawViewModel @Inject constructor(
    private val repository: LawRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _lawsDetail = MutableStateFlow<UiState<LawsCharter?>>(UiState.Loading)
    val lawsDetail: StateFlow<UiState<LawsCharter?>> = _lawsDetail

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private val id = savedStateHandle.get<Int>(LIBRARY_ID_KEY)

    fun clearMessage() {
        _messageFlow.value = null
    }

    init {
        getLawsDetail(id!!)
    }

    fun getLawsDetail(id: Int) {

        viewModelScope.launch {
            _messageFlow.value = Messages.ShowProgressBar
            repository.getLawById(id).collect {
                when (it) {
                    is Either.Left -> {
                        it.left?.let { t ->
                            val message = t.message ?: "Unknown error!"
                            UiState.Error(t, message)
                        }
                    }

                    is Either.Right -> {
                        it.right?.let { data ->
                            UiState.Success(data)
                        }
                    }
                }
            }
        }
    }
}