package com.mvdasker.geeks_pro_mvd.data.remote.model.notification


sealed interface NotificationItem {

    data class MonthItem(val month: Int) : NotificationItem

    data class Notification(
        val id: Int? = null,
        val selection: String? = "",
        val title: String? = "",
        val description: String? = "",
        val createAt: String? = "",
        val isRead: Boolean? = false,
    ) : NotificationItem
}