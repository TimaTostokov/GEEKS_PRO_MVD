package com.mvdasker.geeks_pro_mvd.di

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.repositories.CharterRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.NotificationRepository
import com.mvdasker.geeks_pro_mvd.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLawRepository(sanaripAskerApi: SanaripAskerApi): LawRepository =
        LawRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideCharterRepository(sanaripAskerApi: SanaripAskerApi): CharterRepository =
        CharterRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideNotificationRepository(sanaripAskerApi: SanaripAskerApi): NotificationRepository =
        NotificationRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideManagementRepository(sanaripAskerApi: SanaripAskerApi): ManagementsKrRepository =
        ManagementsKrRepository(sanaripAskerApi, provideAppDispatchers())

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers()

}