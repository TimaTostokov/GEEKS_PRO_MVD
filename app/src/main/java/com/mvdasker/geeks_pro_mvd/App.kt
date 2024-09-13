package com.mvdasker.geeks_pro_mvd

import android.app.Application
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    var userProvider: UserProvider? = null

    override fun onCreate() {
        super.onCreate()
        userProvider = UserProvider(this)
    }

}