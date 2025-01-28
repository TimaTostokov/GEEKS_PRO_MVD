package com.geeksstudio_krmvd.bilimaskerkr.di

import android.content.Context
import com.geeksstudio_krmvd.bilimaskerkr.common.AppDispatchers
import com.geeksstudio_krmvd.bilimaskerkr.common.LanguagePreference
import com.geeksstudio_krmvd.bilimaskerkr.common.UserProvider
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.AuthorizationRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.CharterRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.HistoryRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.LawRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.LibraryRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.ManagementsKrRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.MenuRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.NewsRepository
import com.geeksstudio_krmvd.bilimaskerkr.data.repositories.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLawRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): LawRepository =
        LawRepository(sanaripAskerApi, provideAppDispatchers())

    @Provides
    @Singleton
    fun provideCharterRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): CharterRepository =
        CharterRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideNotificationRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): NotificationRepository =
        NotificationRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideManagementRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): ManagementsKrRepository =
        ManagementsKrRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    @Singleton
    fun provideLibraryRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): LibraryRepository =
        LibraryRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideAuthorizationRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): AuthorizationRepository =
        AuthorizationRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideLanguagePreference(@ApplicationContext context: Context): LanguagePreference {
        return LanguagePreference(context)
    }

    @Provides
    @Singleton
    fun provideMenuRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
        languagePreference: LanguagePreference,
    ): MenuRepository = MenuRepository(sanaripAskerApi, userProvider, languagePreference)

    @Provides
    @Singleton
    fun provideHistoryRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): HistoryRepository =
        HistoryRepository(sanaripAskerApi)

    @Provides
    @Singleton
    fun provideNewsRepository(
        sanaripAskerApi: SanaripAskerApi,
    ): NewsRepository =
        NewsRepository(sanaripAskerApi, provideAppDispatchers())
}