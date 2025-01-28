package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.home.news

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.news.NewsDetail
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _detailState = MutableStateFlow<UiState<NewsDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<NewsDetail>> = _detailState.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private val id = savedStateHandle.get<Int>(ID_KAY)

    init {
        getNews()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun getNews() {
        _messageFlow.value = Messages.ShowProgressBar
        viewModelScope.launch {
            id?.let { newsId ->
                repository.getNewsId(newsId).fold(
                    onSuccess = { newsDetail ->
                        _detailState.value = UiState.Success(newsDetail)
                        _messageFlow.value = Messages.HideProgressBar
                    },
                    onFailure = { error ->
                        Log.e("toli", "во viewModel не пришли данные")
                        _detailState.value = UiState.Error(error, error.message ?: "unknown error!")
                        _messageFlow.value = Messages.NetworkIsDisconnected
                        _messageFlow.value = Messages.HideProgressBar
                    }
                )
            }
        }
    }

    companion object {
        private const val ID_KAY = "id"
    }

}