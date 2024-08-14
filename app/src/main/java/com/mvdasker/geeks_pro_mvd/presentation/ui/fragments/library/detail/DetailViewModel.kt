package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.data.repositories.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {

    private val _noteDetail = MutableStateFlow<Library?>(null)
    val noteDetail: StateFlow<Library?> = _noteDetail

    fun getNoteDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.searchNotes()
                _noteDetail.value = response.find { it.id == id }
            } catch (e: Exception) {
                Log.e("error", "${e.message}")
            }
        }
    }

}