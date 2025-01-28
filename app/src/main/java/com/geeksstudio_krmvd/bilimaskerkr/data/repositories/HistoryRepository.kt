package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import android.util.Log
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.history.HistoryModel
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
) {

    suspend fun getHistory(slug: String): HistoryModel? {
        val response = sanaripAskerApi.getHistory(slug)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("API Error", "Code: ${response.code()}, Message: ${response.message()}")
            null
        }
    }

}