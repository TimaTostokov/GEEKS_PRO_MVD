package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.data.repositories.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: LibraryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteDetail = MutableStateFlow<Library?>(null)
    val noteDetail: StateFlow<Library?> = _noteDetail

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private var id = savedStateHandle.get<Int>(LIBRARY_ID_KEY)

    init {
        getNoteDetail()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun getNoteDetail() {
        _messageFlow.value = Messages.ShowProgressBar
        viewModelScope.launch {
            try {
                val response = id?.let { repository.getLibraryById(it) }
                _noteDetail.value = response
                _messageFlow.value = Messages.HideProgressBar
            } catch (e: Exception) {
                Log.e("error", "${e.message}")
                _messageFlow.value = Messages.NetworkIsDisconnected
                _messageFlow.value = Messages.HideProgressBar
            }
        }
    }

    companion object {
        const val LIBRARY_ID_KEY = "id"
    }

}