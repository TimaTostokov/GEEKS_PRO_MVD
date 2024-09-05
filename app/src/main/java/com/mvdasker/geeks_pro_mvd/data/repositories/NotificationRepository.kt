package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.common.UserProvider
import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val sanaripAskerApi: SanaripAskerApi,
    private val userProvider: UserProvider,
) {

    suspend fun getListNotifications(): List<Notification> =
        sanaripAskerApi.getNotification( userProvider.accessToken).sortedByDescending { it.createAt }

    suspend fun getNotificationById(id:Int):Notification =
        sanaripAskerApi.getNotification(id, userProvider.accessToken)
}