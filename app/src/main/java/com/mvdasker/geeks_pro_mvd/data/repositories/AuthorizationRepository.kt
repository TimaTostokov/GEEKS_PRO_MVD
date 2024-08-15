package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Log
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.Authorization
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun postAuthorization(login: String, password: String): Authorization {
        Log.e("tag", "данные в реп нет", )
        return sanaripAskerApi.postAuthorization(login, password)
    }
}