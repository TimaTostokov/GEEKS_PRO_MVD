package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.AppDispatchers
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsDetail
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.NewsResponse
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import com.mvdasker.geeks_pro_mvd.utils.base.BaseRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val dispatchers: AppDispatchers,
) : BaseRepository() {

    suspend fun getPagingNews(page: Int, pageSize: Int): NewsResponse {
        val response =
            sanaripAskerApi.getNews(page, pageSize)
        return response.copy(results = response.results.sortedByDescending { it.date })
    }

    suspend fun getNewsId(id: Int): Result<NewsDetail> = runCatching {
        withContext(dispatchers.io) {
            sanaripAskerApi.getNewsId(id)
        }
    }

    suspend fun getIsNotReadNotif(): List<Notification> =
        sanaripAskerApi.getNotification()
}