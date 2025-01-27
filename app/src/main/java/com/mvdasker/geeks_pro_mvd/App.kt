package com.mvdasker.geeks_pro_mvd

import android.app.Application
import com.mvdasker.geeks_pro_mvd.common.UserProvider
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