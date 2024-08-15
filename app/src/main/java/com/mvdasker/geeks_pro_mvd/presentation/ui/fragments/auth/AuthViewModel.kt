package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.Authorization
import com.mvdasker.geeks_pro_mvd.data.repositories.AuthorizationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthorizationRepository) :
    ViewModel() {

    private val _authorization = MutableStateFlow<Authorization?>(null)
    val authorization: StateFlow<Authorization?> = _authorization

    fun postAuthorization(login: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.postAuthorization(login, password)
                _authorization.value = response
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Authorization failed: ${e.message}")
                _authorization.value = null
            }
        }
    }
}