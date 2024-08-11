package com.mvdasker.geeks_pro_mvd.data.repositories

import com.mvdasker.geeks_pro_mvd.data.remote.apiservice.SanaripAskerApi
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val sanaripAskerApi: SanaripAskerApi) {

    suspend fun getListNotifications(): List<Notification> {
        return sanaripAskerApi.getNotification()

        @Suppress("UNREACHABLE_CODE")
        val listNotification = listOf(
            Notification(
                0,
                title = "Давайте разбираться вместе: жизнь прекрасна ",
                month = "month",
                selection = "Документы",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "2024-07-27T12:15:21.044941Z",
                isRead = false
            ),
            Notification(
                0,
                title = "Давайте разбираться вместе: жизнь прекрасна ",
                month = "month",
                selection = "Библиотека",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "2024-07-27T12:15:21.044941Z",
                isRead = false
            ),
            Notification(
                0,
                title = "Давайте разбираться вместе: жизнь прекрасна ",
                month = "month",
                selection = "Документы",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "2024-06-27T12:15:21.044941Z",
                isRead = true
            ),

            Notification(
                0,
                title = "Давайте разбираться вместе: жизнь прекрасна ",
                month = "month",
                selection = "Библиотека",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "2024-06-27T12:15:21.044941Z",
                isRead = true
            ),

            Notification(
                0,
                title = "Давайте разбираться вместе: жизнь прекрасна ",
                month = "month",
                selection = "Библиотека",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "2024-05-27T12:15:21.044941Z",
                isRead = true
            ),
            Notification(
                0,
                title = "Давайте разбираться вместе: жизнь прекрасна ",
                month = "month",
                selection = "Документы",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "2024-05-27T12:15:21.044941Z",
                isRead = true
            )
        )
        return listNotification
    }
}