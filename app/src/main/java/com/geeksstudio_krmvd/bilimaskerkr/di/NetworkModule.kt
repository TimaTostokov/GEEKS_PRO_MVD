package com.geeksstudio_krmvd.bilimaskerkr.di

import com.geeksstudio_krmvd.bilimaskerkr.common.Constants.BASE_URL
import com.geeksstudio_krmvd.bilimaskerkr.common.Constants.NETWORK_TIMEOUT
import com.geeksstudio_krmvd.bilimaskerkr.common.LanguagePreference
import com.geeksstudio_krmvd.bilimaskerkr.common.UserProvider
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLanguageInterceptor(
        languagePreference: LanguagePreference,
        userProvider: UserProvider,
    ): LanguageInterceptor = LanguageInterceptor(languagePreference, userProvider)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        languageInterceptor: LanguageInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(languageInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .callTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): SanaripAskerApi =
        retrofit.create(SanaripAskerApi::class.java)
}