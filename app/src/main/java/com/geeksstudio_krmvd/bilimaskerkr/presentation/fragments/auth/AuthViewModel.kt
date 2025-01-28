package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.authorization.AuthState
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.AuthorizationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthorizationRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun onLoginClick(login: String, password: String) {
        _state.update {
            it.copy(
                isLoginValid = login.isNotEmpty() && pattern.matcher(login).matches(),
                isPasswordValid = password.isNotEmpty() && pattern.matcher(password)
                    .matches()
            )
        }

        if (_state.value.run { !isLoginValid || !isPasswordValid }) {
            return
        }
        viewModelScope.launch {
            try {
                repository.postAuthorization(login, password)
                Log.d("AuthResponse", "User data: ${_state.value}")
                _state.update { it.copy(needNavigateToHome = true) }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Authorization failed: ${e.message}")
                if (e is HttpException) {
                    if (e.code() in 400..500) {
                        _state.update { it.copy(isLoginValid = false, isPasswordValid = false) }
                    }
                }
            }
        }
    }

    fun onLoginChanged() {
        _state.update { it.copy(isLoginValid = true) }
    }

    fun onPasswordChanged() {
        _state.update { it.copy(isPasswordValid = true) }
    }

    fun onNavigatedToHome() {
        _state.update { it.copy(needNavigateToHome = false) }
    }

    companion object {
        private val pattern = Pattern.compile("^[a-zA-Z0-9.@]{6,}\$")
    }

}