package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.mangements.Governance
import javax.inject.Inject

class ManagementsKrRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
) {

    suspend fun fetchConstitutionsKr(): List<Governance> {
        return sanaripAskerApi.fetchConstitutionsKr()
    }

    suspend fun fetchConstitutionsVVKr(jobTittle: String? = null): List<Governance> {
        return sanaripAskerApi.fetchConstitutionsVVKr(jobTittle)
    }

    suspend fun fetchConstitutionsMIAKr(jobTittle: String? = null): List<Governance> {
        return sanaripAskerApi.fetchConstitutionsMVDKr(jobTittle)
            .reversed()
    }

}