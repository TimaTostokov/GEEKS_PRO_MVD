package com.mvdasker.geeks_pro_mvd.di

import android.content.Context
import android.content.SharedPreferences
import com.mvdasker.geeks_pro_mvd.common.Constants.BASE_URL
import com.mvdasker.geeks_pro_mvd.common.Pref
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .callTimeout(60L, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): SanaripAskerApi =
        retrofit.create(SanaripAskerApi::class.java)

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Pref.PREF_NAME, Context.MODE_PRIVATE)
    }

}