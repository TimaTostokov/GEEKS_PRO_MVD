package com.mvdasker.geeks_pro_mvd.data.remote.model.notification

sealed interface NotificationItem {

    data class MonthItem(val month: Int) : NotificationItem

    data class Notification(
        val id: Int? = null,
        val section: String? = null,
        val title: String? = null,
        val createAt: String? = null,
        val isRead: Boolean = false,
    ) : NotificationItem

}