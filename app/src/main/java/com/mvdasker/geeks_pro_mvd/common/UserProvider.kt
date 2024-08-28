package com.mvdasker.geeks_pro_mvd.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserProvider @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs = context.getSharedPreferences("user.store", Context.MODE_PRIVATE)

    val accessToken: String
        get() = prefs.getString(ACCESS_TOKEN_KEY, "").orEmpty()

    fun saveAccess(accessToken: String) {
        prefs.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
    }

    fun saveUserName(name: String) {
        prefs.edit().putString(USER_NAME_KEY, name).apply()
    }

    fun saveUserPhoto(userPhoto: String) {
        prefs.edit().putString(USER_PHOTO_KEY, userPhoto).apply()
    }

    fun getAccess(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, "-2")
    }

    fun getUserName(): String {
        return prefs.getString(USER_NAME_KEY, "").orEmpty()
    }

    fun getUserPhoto(): String {
        return prefs.getString(USER_PHOTO_KEY, "").orEmpty()
    }

    companion object {
        private const val USER_NAME_KEY = "USER_NAME_KEY"
        private const val USER_PHOTO_KEY = "USER_PHOTO_KEY"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }

}