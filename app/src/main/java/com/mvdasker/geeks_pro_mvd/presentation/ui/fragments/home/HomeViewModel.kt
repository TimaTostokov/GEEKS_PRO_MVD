package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.AppDispatchers
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.News
import com.mvdasker.geeks_pro_mvd.data.repositories.NewsRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    private val _newsState = MutableStateFlow<UiState<List<News>>>(UiState.Loading)
    val newsState = _newsState.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        fetchNews()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun fetchNews() {
        viewModelScope.launch(dispatchers.io) {
            try {
                val result = repository.getNews().results
                if (result != null) {
                    _newsState.value = UiState.Success(result)
                }
            } catch (t: Throwable) {
                _newsState.value = UiState.Error(throwable = t, message = "An error occurred")
                _messageFlow.value = Messages.NetworkIsDisconnected
            }
        }
    }

}