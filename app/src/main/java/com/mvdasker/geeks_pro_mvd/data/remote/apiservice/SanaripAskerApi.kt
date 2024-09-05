package com.mvdasker.geeks_pro_mvd.data.remote.apiservice

import com.mvdasker.geeks_pro_mvd.common.Constants.AUTHORIZATION_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.AUTHORIZATION_GET_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.CHARTERS_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.CONSTITUTIONS_DETAIL_ENDPOINT
import com.mvdasker.geeks_pro_mvd.common.Constants.CONSTITUTIONS_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.END_POINT_LIBRARY
import com.mvdasker.geeks_pro_mvd.common.Constants.HISTORY_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.LAW_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.LIBRARY_DETAIL_ENDPOINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_MVD_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.MANAGEMENT_VV_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NEWS_DETAIL_ENDPOINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NEWS_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NOTIFICATION_DETAIL_END_POINT
import com.mvdasker.geeks_pro_mvd.common.Constants.NOTIFICATION_END_POINT
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.AuthResponse
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.Authorization
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.User
import com.mvdasker.geeks_pro_mvd.data.remote.model.charter.Charter
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.Constitutions
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.ConstitutionsChapter
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryModel
import com.mvdasker.geeks_pro_mvd.data.remote.model.law.Law
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsDetail
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsResponse
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SanaripAskerApi {

    @GET(LAW_END_POINT)
    suspend fun getLaw(
        @Header("Authorization") accessToken: String,
    ): List<Law>

    @GET(CHARTERS_END_POINT)
    suspend fun getCharters(
        @Header("Authorization") accessToken: String,
    ): List<Charter>

    @GET(NOTIFICATION_END_POINT)
    suspend fun getNotification(
        @Header("Authorization") accessToken: String,
    ): List<Notification>

    @GET(NOTIFICATION_DETAIL_END_POINT)
    suspend fun getNotification(
        @Path("id") id: Int,
        @Header("Authorization") accessToken: String,
    ): Notification

    @GET(HISTORY_END_POINT)
    suspend fun getHistory(
        @Path("slug") slug: String,
        @Header("Authorization") accessToken: String,
    ): Response<HistoryModel>

    @GET(MANAGEMENT_END_POINT)
    suspend fun fetchConstitutionsKr(
        @Header("Authorization") accessToken: String,
    ): List<Governance>

    @GET(MANAGEMENT_VV_END_POINT)
    suspend fun fetchConstitutionsVVKr(
        @Header("Authorization") accessToken: String,
        @Query("job_tittle") jobTittle: String? = null,
    ): List<Governance>

    @GET(MANAGEMENT_MVD_END_POINT)
    suspend fun fetchConstitutionsMVDKr(
        @Header("Authorization") accessToken: String,
        @Query("job_tittle") jobTittle: String? = null,
    ): List<Governance>

    @GET(END_POINT_LIBRARY)
    suspend fun getLibrary(
        @Header("Authorization") accessToken: String,
        @Query("title") title: String? = null,
        @Query("description") description: String? = null,
    ): List<Library>

    @GET(LIBRARY_DETAIL_ENDPOINT)
    suspend fun getLibraryById(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    ): Library

    @POST(AUTHORIZATION_END_POINT)
    suspend fun postAuthorization(
        @Body data: Authorization,
    ): AuthResponse

    @GET(AUTHORIZATION_GET_END_POINT)
    suspend fun getUserById(
        @Header("Authorization") accessToken: String,
        @Path("id") userId: Int,
    ): User?

    @GET(NEWS_END_POINT)
    suspend fun getNews(
        @Header("Authorization") accessToken: String,
    ): NewsResponse

    @GET(NEWS_DETAIL_ENDPOINT)
    suspend fun getNewsId(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    ): NewsDetail

    suspend fun getNewsId(@Path("id") id: Int): NewsDetail

    @GET(CONSTITUTIONS_END_POINT)
    suspend fun getConstitution(
        @Header("Authorization") accessToken: String,
    ): List<Constitutions>

    @GET(CONSTITUTIONS_DETAIL_ENDPOINT)
    suspend fun getConstitutionById(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
    ): ConstitutionsChapter

}