package com.mvdasker.geeks_pro_mvd.data.remote.apiservice

import com.mvdasker.geeks_pro_mvd.common.Constants.AUTHORIZATION_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.AUTHORIZATION_GET_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.CHARTERS_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.END_POINT_LIBRARY
import com.mvdasker.geeks_pro_mvd.common.Constants.HISTORY_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.LAW_END_ID_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.LAW_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.LIBRARY_DETAIL_ENDPOINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_MVD_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_VV_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NEWS_DETAIL_ENDPOINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NEWS_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NOTIFICATION_END_POINT
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.AuthResponse
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.Authorization
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.User
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryModel
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.LawsCharter
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsDetail
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsResponse
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SanaripAskerApi {

    @GET(LAW_END_POINT)
    suspend fun getLaw(): List<Law>

    @GET(LAW_END_ID_POINT)
    suspend fun getLawByTitle(@Query("id") id: Int): LawsCharter


    @GET(CHARTERS_END_POINT)
    suspend fun getCharters(): List<Charter>

    @GET(NOTIFICATION_END_POINT)
    suspend fun getNotification(): List<Notification>

    @GET(HISTORY_END_POINT)
    suspend fun getHistory(@Path("slug") slug: String): Response<HistoryModel>

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

    @GET(LIBRARY_DETAIL_ENDPOINT)
    suspend fun getLibraryById(@Path("id") id: Int): Library

    @POST(AUTHORIZATION_END_POINT)
    suspend fun postAuthorization(
        @Body data: Authorization,
    ): AuthResponse

    @GET(AUTHORIZATION_GET_END_POINT)
    suspend fun getUserById(
        @Path("pk") userId: Int
    ): User?

    @GET(NEWS_END_POINT)
    suspend fun getNews(): NewsResponse

    @GET(NEWS_DETAIL_ENDPOINT)
    suspend fun getNewsId(@Path("id") id: Int): NewsDetail
}