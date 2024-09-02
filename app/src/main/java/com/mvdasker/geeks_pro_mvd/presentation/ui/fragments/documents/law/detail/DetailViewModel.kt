package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.detail

import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.LawsCharter
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: LawRepository): BaseViewModel() {
    private val _lawDetail = MutableStateFlow<UiState<LawsCharter>>(UiState.Loading)
    val lawDetail = _lawDetail.asStateFlow()

//   suspend fun fetchLawDetail(id: Int) {
//        viewModelScope.launch {
//            repository.getLawByTitle(id)
//                .collectFlowAsState(_lawDetail)
//        }
//    }
}