package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsDetail
import com.mvdasker.geeks_pro_mvd.data.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _detailState = MutableLiveData<UiState<NewsDetail>>()
    val detailState: LiveData<UiState<NewsDetail>> = _detailState

    private val id = savedStateHandle.get<String>(ID_KAY)

    fun setId(id: String) {
        savedStateHandle[ID_KAY] = id
    }

    init {
        viewModelScope.launch {
            id?.let { newsId ->
                repository.getNewsId(newsId.toInt()).fold(
                    onSuccess = { newsDetail ->
                        // Успешное получение данных, обновляем состояние
                        _detailState.value = UiState.Success(newsDetail)
                    },
                    onFailure = { error ->
                        Log.e("tag", "во viewModel не пришли данные", )
                        _detailState.value = UiState.Error(error, error.message ?: "unknown error!")
                    }
                )
            }
        }
    }

    companion object {
        private const val ID_KAY = "id"
    }
}