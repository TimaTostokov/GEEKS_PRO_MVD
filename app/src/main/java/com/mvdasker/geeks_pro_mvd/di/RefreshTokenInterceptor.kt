package com.mvdasker.geeks_pro_mvd.di

import android.util.Log
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val userProvider: UserProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "JWT ${userProvider.accessToken}")
            .build()
        try {
            chain.proceed(request)
        } catch (t: Throwable) {
            if (t is HttpException && t.code() == 403) {
                Log.e("ololo", "intercept: refreshToken")
            }
        }
        return chain.proceed(request)
    }

}