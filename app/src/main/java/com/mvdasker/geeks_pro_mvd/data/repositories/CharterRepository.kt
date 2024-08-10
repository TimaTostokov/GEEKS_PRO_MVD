package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import javax.inject.Inject

class CharterRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun getListCharters(): List<Charter> =
        sanaripAskerApi.getCharters()
}