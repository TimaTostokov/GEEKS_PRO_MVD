package com.mvdasker.geeks_pro_mvd.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Either
import com.mvdasker.geeks_pro_mvd.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected open fun <T> Flow<Either<Throwable, List<T>>>.collectFlowAsState(
        state: MutableStateFlow<UiState<List<T>>>,
    ) {
        viewModelScope.launch {
            this@collectFlowAsState.collect { either ->
                when (either) {
                    is Either.Left -> {
                        either.left?.let { throwable ->
                            val message = throwable.message ?: "Unknown error!"
                            state.value = UiState.Error(throwable, message)
                        }
                    }

                    is Either.Right -> {
                        either.right?.let { data ->
                            state.value = UiState.Success(data) // data уже является списком
                        }
                    }
                }
            }
        }
    }

}