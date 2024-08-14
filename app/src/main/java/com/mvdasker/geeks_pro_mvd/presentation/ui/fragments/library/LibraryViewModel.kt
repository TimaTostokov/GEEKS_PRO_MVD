package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library

import android.util.Log
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
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {

    private val _libraries = MutableStateFlow<List<Library>?>(null)
    val libraries: StateFlow<List<Library>?> = _libraries

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        getLibraries()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun getLibraries(title: String? = null, description: String? = null) {
        viewModelScope.launch {
            try {
                val result = repository.searchNotes(title, description)
                _libraries.value = result
                Log.e("librariesdan", "${_libraries.value}")
            } catch (e: Exception) {
                _messageFlow.value = Messages.NetworkIsDisconnected
                _libraries.value = emptyList()
                Log.e("error", "Exception occurred: ${e.message}")
            }
        }
    }

}