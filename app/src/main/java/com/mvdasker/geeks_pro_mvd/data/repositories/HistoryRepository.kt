package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Log
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryModel
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

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