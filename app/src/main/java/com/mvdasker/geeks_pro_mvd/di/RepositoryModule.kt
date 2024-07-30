package com.mvdasker.geeks_pro_mvd.di

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.repositories.CharterRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideLawRepository(sanaripAskerApi: SanaripAskerApi): LawRepository =
        LawRepository(sanaripAskerApi)

    @Provides
    fun provideCharterRepository(sanaripAskerApi: SanaripAskerApi): CharterRepository =
        CharterRepository(sanaripAskerApi)

    @Provides
    fun provideNotificationRepository(sanaripAskerApi: SanaripAskerApi): NotificationRepository =
        NotificationRepository(sanaripAskerApi)
}