package com.mvdasker.geeks_pro_mvd.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.utils.Either
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
abstract class BaseViewModel : ViewModel() {

    protected open fun <T> Flow<Either<Throwable, T>>.collectFlowAsState(
        state: MutableStateFlow<UiState<T>>,

    ) {
        viewModelScope.launch {
            this@collectFlowAsState.collect {
                when (it) {
                    is Either.Left -> {
                        it.left?.let { t ->
                            val message = t.message ?: "Unknown error!"
                            state.value = UiState.Error(t, message)
                        }
                    }

                    is Either.Right -> {
                        it.right?.let { data ->
                            state.value = UiState.Success(data)
                        }
                    }
                }
            }
        }
    }

}