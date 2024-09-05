package com.mvdasker.geeks_pro_mvd.di

import com.mvdasker.geeks_pro_mvd.common.UserProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val userProvider: UserProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
            .addHeader("Authorization", "JWT ${userProvider.accessToken}")

        val response = chain.proceed(builder.build())

        return response
    }

}