package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.repositories.DocumentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentsViewModel @Inject constructor(private val repository: DocumentsRepository) :
    ViewModel() {

    private val _notReadNotifCount = MutableStateFlow(0)
    val notReadNotifCount: StateFlow<Int> get() = _notReadNotifCount

    init {
        updateNotReadNotifCount()
    }

    private fun updateNotReadNotifCount() {
        viewModelScope.launch {
            val result = repository.getIsNotReadNotif()
            val notReadList = result.filter { !it.readed }
            _notReadNotifCount.value = notReadList.size
        }
    }
}