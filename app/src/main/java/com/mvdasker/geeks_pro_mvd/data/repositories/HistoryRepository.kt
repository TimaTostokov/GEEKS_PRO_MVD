package com.mvdasker.geeks_pro_mvd.data.repositories

import android.util.Log
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryResponse
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun getHistory(pk: Int): HistoryResponse? {
        val response = sanaripAskerApi.getHistory(pk)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("API Error", "Code: ${response.code()}, Message: ${response.message()}")
            null
        }
    }

}