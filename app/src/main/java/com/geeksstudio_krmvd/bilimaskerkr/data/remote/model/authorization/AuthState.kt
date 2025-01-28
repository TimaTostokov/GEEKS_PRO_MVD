package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.authorization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthState(
    val isLoginValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val needNavigateToHome: Boolean = false,
    val user: AuthResponse? = null
) : Parcelable