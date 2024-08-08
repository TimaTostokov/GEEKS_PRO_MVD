package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun getListNotifications(): List<Notification> {
        return sanaripAskerApi.getNotification()

//        val list = listOf(
//            Notification(
//                0,
//                title = "test",
//                description = "text description",
//                createAt = "2024-07-27T12:15:21.044941Z",
//                isRead = false
//            ),
//            Notification(
//                0,
//                title = "test",
//                description = "text description",
//                createAt = "2024-07-27T12:15:21.044941Z",
//                isRead = false
//            ),
//            Notification(
//                0,
//                title = "test",
//                description = "text description",
//                createAt = "2024-06-27T12:15:21.044941Z",
//                isRead = true
//            ),
//
//            Notification(
//                0,
//                title = "test",
//                description = "text description",
//                createAt = "2024-06-27T12:15:21.044941Z",
//                isRead = true
//            ),
//
//            Notification(
//                0,
//                title = "test",
//                description = "text description",
//                createAt = "2024-05-27T12:15:21.044941Z",
//                isRead = true
//            ),
//            Notification(
//                0,
//                title = "test",
//                description = "text description",
//                createAt = "2024-05-27T12:15:21.044941Z",
//                isRead = true
//            )
//        )
//        return list
    }
}