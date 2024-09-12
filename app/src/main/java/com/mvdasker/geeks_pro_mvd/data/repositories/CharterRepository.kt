package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
import javax.inject.Inject

class CharterRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
) : BaseRepository() {

    fun getListChartersFlow() = doRequest {
        val charters = sanaripAskerApi.getCharters()
        charters.reversed()
    }

}