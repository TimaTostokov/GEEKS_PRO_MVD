package com.mvdasker.geeks_pro_mvd.common

import android.content.Context
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.AuthResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserProvider @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs = context.getSharedPreferences("user.store", Context.MODE_PRIVATE)

    val accessToken: String
        get() = prefs.getString(ACCESS_TOKEN_KEY, "").orEmpty()

    fun saveUserId(userId: Int) {
        prefs.edit().putInt(USER_ID, userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }

    companion object {
        private const val USER_NAME_KEY = "USER_NAME_KEY"
        private const val USER_PHOTO_URL_KEY = "USER_PHOTO_URL_KEY"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
        private const val USER_ID = "USER_ID"
    }

}