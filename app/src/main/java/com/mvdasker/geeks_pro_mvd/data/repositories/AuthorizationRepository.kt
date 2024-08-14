package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun postAuthorization(login: String, password: String) {
        sanaripAskerApi.postAuthorization()
    }
}