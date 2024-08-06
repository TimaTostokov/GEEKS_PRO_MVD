package com.mvdasker.geeks_pro_mvd.data.remote.apiservice

import com.mvdasker.geeks_pro_mvd.data.remote.model.Charter
import com.mvdasker.geeks_pro_mvd.data.remote.model.Law
import com.mvdasker.geeks_pro_mvd.data.remote.model.Notification
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface SanaripAskerApi {

    @GET("docs/law")
    suspend fun getLaw(): List<Law>

    @GET("docs/charter")
    suspend fun getCharters(): List<Charter>

    @GET("docs/notification")
    suspend fun getNotification(): List<Notification>
}