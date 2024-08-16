package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun getListNotifications(): List<Notification> =
        sanaripAskerApi.getNotification().sortedByDescending { it.createAt }
}