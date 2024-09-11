package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import javax.inject.Inject

class ManagementsKrRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider
) {

    suspend fun fetchConstitutionsKr(): List<Governance> {
        return sanaripAskerApi.fetchConstitutionsKr(userProvider.accessToken)
    }

    suspend fun fetchConstitutionsVVKr(jobTittle: String? = null): List<Governance> {
        return sanaripAskerApi.fetchConstitutionsVVKr(userProvider.accessToken, jobTittle)
    }

    suspend fun fetchConstitutionsMIAKr(jobTittle: String? = null): List<Governance> {
        return sanaripAskerApi.fetchConstitutionsMVDKr(userProvider.accessToken, jobTittle).reversed()
    }

}