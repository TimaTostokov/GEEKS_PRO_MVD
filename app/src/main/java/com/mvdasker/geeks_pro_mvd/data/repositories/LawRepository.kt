package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import javax.inject.Inject

class LawRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider
) {

    suspend fun getLaw(): List<Law> = sanaripAskerApi.getLaw(userProvider.accessToken)
}