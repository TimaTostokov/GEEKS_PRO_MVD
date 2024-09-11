package com.mvdasker.geeks_pro_mvd.di

import com.mvdasker.geeks_pro_mvd.common.LanguagePreference
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LanguageInterceptor @Inject constructor(
    private val languagePreference: LanguagePreference,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val languageCode = languagePreference.getLanguage ?: "ru"

        val newRequest = originalRequest.newBuilder()
            .addHeader("Accept-Language", languageCode)
            .build()

        return chain.proceed(newRequest)
    }

}