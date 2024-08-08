package com.mvdasker.geeks_pro_mvd.data.remote.apiservice

import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import retrofit2.http.GET

private const val MANAGEMENT_END_POINT = "docs/governance/kr/"
private const val LAW_END_POINT = "docs/law/"
private const val CHARTERS_END_POINT = "docs/charter/"
private const val NOTIFICATION_END_POINT = "docs/notification/"
private const val MANAGEMENT_VV_END_POINT = "docs/governance/vvmvdkr/"


interface SanaripAskerApi {

    @GET(LAW_END_POINT)
    suspend fun getLaw(): List<Law>

    @GET(CHARTERS_END_POINT)
    suspend fun getCharters(): List<Charter>

    @GET(NOTIFICATION_END_POINT)
    suspend fun getNotification(): List<Notification>

    @GET(MANAGEMENT_END_POINT)
    suspend fun fetchConstitutionsKr(): List<Governance>

    @GET(MANAGEMENT_VV_END_POINT)
    suspend fun fetchConstitutionsVVKr(): List<Governance>
}