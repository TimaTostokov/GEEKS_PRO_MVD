package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import javax.inject.Inject

class LawRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi
) {

    suspend fun getLaw(): List<Law> =
        sanaripAskerApi.getLaw()
}