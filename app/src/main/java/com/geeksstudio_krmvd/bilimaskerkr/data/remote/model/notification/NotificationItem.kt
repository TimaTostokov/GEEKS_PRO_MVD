package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.notification

sealed interface NotificationItem {

    data class MonthItem(val month: Int) : NotificationItem

    data class Notification(
        val id: Int? = null,
        val section: String? = null,
        val title: String? = null,
        val createAt: String? = null,
        val readed: Boolean = false,
        val notificationId: Int? = null,
        val notificationType: String? = null
    ) : NotificationItem

}