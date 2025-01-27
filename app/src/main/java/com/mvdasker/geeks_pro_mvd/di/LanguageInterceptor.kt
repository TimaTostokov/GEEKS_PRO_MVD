package com.mvdasker.geeks_pro_mvd.di

import android.util.Log
import com.mvdasker.geeks_pro_mvd.common.LanguagePreference
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LanguageInterceptor @Inject constructor(
    private val languagePreference: LanguagePreference,
    private val userProvider: UserProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val languageCode = languagePreference.getLanguage ?: "ru"
        Log.d("LanguageInterceptor", "Using language: $languageCode")

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", userProvider.accessToken)
            .addHeader("Accept-Language", languageCode)
            .build()

        return chain.proceed(newRequest)
    }

}