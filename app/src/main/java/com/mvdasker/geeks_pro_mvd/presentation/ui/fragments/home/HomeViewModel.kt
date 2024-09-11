package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.NewsPagingSource
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsResponse
import com.mvdasker.geeks_pro_mvd.data.repositories.NewsRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
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


//    init {
//        fetchNews(currentPage)
//    }

//    private fun fetchNews(page: Int) {
//        viewModelScope.launch {
//            try {
//                val response = repository.getNews(page)
//                _newsState.value = UiState.Success(response)
//            } catch (e: Exception) {
//                _newsState.value = UiState.Error(throwable = e, message = "Error")
//                _messageFlow.value = Messages.NetworkIsDisconnected
//            }
//        }
//    }
//
//    fun loadNextPage() {
//        fetchNews(currentPage + 1)
//    }
//
//    fun loadNext() {
//        fetchNews(currentPage - 1)
//    }
//
//    private fun updateNotReadNotifCount() {
//        viewModelScope.launch {
//            val result = repository.getIsNotReadNotif()
//            val notReadList = result.filter { !it.isRead }
//            _notReadNotifCount.value = notReadList.size
//        }
//    }

}