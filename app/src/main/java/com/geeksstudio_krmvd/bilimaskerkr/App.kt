package com.geeksstudio_krmvd.bilimaskerkr

import android.app.Application
import com.geeksstudio_krmvd.bilimaskerkr.common.UserProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var userProvider: UserProvider

    override fun onCreate() {
        super.onCreate()
        userProvider = UserProvider(this)
        userProvider.clearAccessToken()
    }

}