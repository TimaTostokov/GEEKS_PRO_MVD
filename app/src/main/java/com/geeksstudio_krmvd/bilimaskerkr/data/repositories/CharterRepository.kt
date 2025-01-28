package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.utils.base.BaseRepository
import javax.inject.Inject

class CharterRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
) : BaseRepository() {

    fun getListChartersFlow() = doRequest {
        val charters = sanaripAskerApi.getCharters()
        charters.reversed()
    }

}