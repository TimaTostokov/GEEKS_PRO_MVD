package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvdasker.geeks_pro_mvd.data.remote.model.DataItem
import com.mvdasker.geeks_pro_mvd.data.repositories.NewsRepository
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState

class HomeViewModel : ViewModel() {

    private val repository = NewsRepository()
    private val _newsLivData = MutableLiveData<UiState<List<DataItem>>>()
    val newsLiveData: LiveData<UiState<List<DataItem>>> = _newsLivData

    init {
        getNews()
    }

    private fun getNews() {
        _newsLivData.value = UiState.Loading
        repository.addNews()
        _newsLivData.value = UiState.Success(repository.newsList)
    }

}