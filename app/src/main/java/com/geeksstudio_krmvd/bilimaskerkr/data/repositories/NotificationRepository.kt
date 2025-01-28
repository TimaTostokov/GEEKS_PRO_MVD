package com.geeksstudio_krmvd.bilimaskerkr.data.repositories

import com.geeksstudio_krmvd.bilimaskerkr.data.remote.apiservice.SanaripAskerApi
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.notification.Notification
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
) {

    suspend fun getListNotifications(): List<Notification> =
        sanaripAskerApi.getNotification().sortedByDescending { it.createAt }

    suspend fun getNotificationById(id: Int): Notification =
        sanaripAskerApi.getNotification(id)
}