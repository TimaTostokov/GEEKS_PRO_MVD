package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.news.NewsResponse
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.NewsRepository
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.home.paging.NewsPagingSource
import com.geeksstudio_krmvd.bilimaskerkr.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
) : BaseViewModel() {

    private val _newsState = MutableStateFlow<UiState<NewsResponse>>(UiState.Loading)
    val newsState: StateFlow<UiState<NewsResponse>> = _newsState

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private val _notReadNotifCount = MutableStateFlow(0)
    val notReadNotifCount: StateFlow<Int> get() = _notReadNotifCount

    fun clearMessage() {
        _messageFlow.value = null
    }

    val newsPager =
        Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = true)) {
            NewsPagingSource(repository)
        }.flow.cachedIn(viewModelScope)

    init {
        updateNotReadNotifCount()
    }

    private fun updateNotReadNotifCount() {
        viewModelScope.launch {
            try {
                val result = repository.getIsNotReadNotif()
                val notReadList = result.filter { !it.readed }
                _notReadNotifCount.value = notReadList.size
            } catch (e: Exception) {
                _notReadNotifCount.value = 0
            }
        }
    }

}