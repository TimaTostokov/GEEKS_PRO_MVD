package com.mvdasker.geeks_pro_mvd.data.remote.apiservice

import com.mvdasker.geeks_pro_mvd.common.Constants.AUTHORIZATION_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.CHARTERS_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.END_POINT_LIBRARY
import com.mvdasker.geeks_pro_mvd.common.Constants.LAW_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_MVD_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NOTIFICATION_END_POINT
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    suspend fun fetchConstitutionsVVKr(
        @Query("job_tittle") jobTittle: String? = null
    ): List<Governance>

    @GET(MANAGEMENT_MVD_END_POINT)
    suspend fun fetchConstitutionsMVDKr(
        @Query("job_tittle") jobTittle: String? = null
    ): List<Governance>

    @GET(END_POINT_LIBRARY)
    suspend fun getLibrary(
        @Query("title") title: String? = null,
        @Query("description") description: String? = null
    ): List<Library>

    @POST(AUTHORIZATION_END_POINT)
    suspend fun postAuthorization(
        @Query("login") login: String? = null,
        @Query("password") password: String? = null
    )

}