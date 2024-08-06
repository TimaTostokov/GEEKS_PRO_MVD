package com.mvdasker.geeks_pro_mvd.di

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    fun provideApi(retrofit: Retrofit): SanaripAskerApi =
        retrofit.create(SanaripAskerApi::class.java)

    companion object {
        const val BASE_URL = "http://16.171.160.120/api/v1/"
    }

}