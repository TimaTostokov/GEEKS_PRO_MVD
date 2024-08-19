package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Log
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.Authorization
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
//    private val userProvider: UserProvider,
) {

    suspend fun postAuthorization(username: String, password: String) {
        Log.e("tag", "данные в реп нет")
        sanaripAskerApi.postAuthorization(Authorization(username, password))
//            .also { authResponse ->
//                userProvider.saveAuthResponse(authResponse)
//            }
    }

}