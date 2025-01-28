package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.common.UserProvider
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.authorization.Authorization
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider,
) {

    suspend fun postAuthorization(username: String, password: String) {
        sanaripAskerApi.postAuthorization(Authorization(username, password))
            .also { authResponse ->
                authResponse.access?.let { userProvider.saveAccess(it) }
            }
    }

}