package com.mvdasker.geeks_pro_mvd.di

import android.content.Context
import com.mvdasker.geeks_pro_mvd.common.AppDispatchers
import com.mvdasker.geeks_pro_mvd.common.LanguagePreference
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.repositories.AuthorizationRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.CharterRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.ConstitutionsRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.DocumentsRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.HistoryRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.LawRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.LibraryRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.ManagementsKrRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.MenuRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.NewsRepository
import com.mvdasker.geeks_pro_mvd.data.repositories.NotificationRepository
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
        userProvider: UserProvider,
    ): LawRepository =
        LawRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideCharterRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): CharterRepository =
        CharterRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideNotificationRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): NotificationRepository =
        NotificationRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideManagementRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): ManagementsKrRepository =
        ManagementsKrRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    @Singleton
    fun provideLibraryRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): LibraryRepository =
        LibraryRepository(sanaripAskerApi, userProvider)

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
        languagePreference: LanguagePreference
    ): MenuRepository = MenuRepository(sanaripAskerApi, userProvider, languagePreference)

    @Provides
    @Singleton
    fun provideHistoryRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): HistoryRepository =
        HistoryRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideNewsRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): NewsRepository =
        NewsRepository(sanaripAskerApi, provideAppDispatchers(), userProvider)

    @Provides
    @Singleton
    fun provideDocumentsRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider,
    ): DocumentsRepository =
        DocumentsRepository(sanaripAskerApi, userProvider)

    @Provides
    @Singleton
    fun provideConstitutionRepository(
        sanaripAskerApi: SanaripAskerApi,
        userProvider: UserProvider
    ): ConstitutionsRepository =
        ConstitutionsRepository(sanaripAskerApi, userProvider, provideAppDispatchers())
}