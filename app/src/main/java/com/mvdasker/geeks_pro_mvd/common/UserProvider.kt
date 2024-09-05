package com.mvdasker.geeks_pro_mvd.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserProvider @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs = context.getSharedPreferences("user.store", Context.MODE_PRIVATE)

    val accessToken: String
        get() = prefs.getString(ACCESS_TOKEN_KEY, "-1").orEmpty()

    fun saveAccess(accessToken: String) {
        prefs.edit().putString(ACCESS_TOKEN_KEY, "JWT $accessToken").apply()
    }

    fun saveUserName(name: String) {
        prefs.edit().putString(USER_NAME_KEY, name).apply()
    }

    fun saveUserPhoto(userPhoto: String) {
        prefs.edit().putString(USER_PHOTO_KEY, userPhoto).apply()
    }

    fun getUserName(): String {
        return prefs.getString(USER_NAME_KEY, "-2").orEmpty()
    }

    fun getUserPhoto(): String {
        return prefs.getString(USER_PHOTO_KEY, "-3").orEmpty()
    }

    companion object {
        private const val USER_NAME_KEY = "USER_NAME_KEY"
        private const val USER_PHOTO_KEY = "USER_PHOTO_KEY"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
    }

}