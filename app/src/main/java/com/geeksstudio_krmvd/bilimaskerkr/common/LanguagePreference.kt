package com.geeksstudio_krmvd.bilimaskerkr.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class LanguagePreference @Inject constructor(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("my_language", Context.MODE_PRIVATE)

    val getLanguage: String?
        get() = preferences.getString("language_", "ru").also {
            Log.d("LanguagePreference", "Retrieved language: $it")
        }

    fun saveLanguage(s: String) {
        preferences.edit().putString("language_", s).apply()
        Log.d("LanguagePreference", "Saved language: $s")
    }

    companion object {
        @Volatile
        private var instance: LanguagePreference? = null

        fun getInstance(context: Context): LanguagePreference {
            return instance ?: synchronized(this) {
                instance ?: LanguagePreference(context).also { instance = it }
            }
        }
    }

}