package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Log
import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
import javax.inject.Inject

class CharterRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider
) : BaseRepository() {

    fun getListChartersFlow() = doRequest {
        val charters = sanaripAskerApi.getCharters(userProvider.accessToken)
        Log.d("tash", userProvider.accessToken)
        charters.reversed()
    }

}