package com.mvdasker.geeks_pro_mvd.data.remote.model.notification

data class NotificationState(
    val error: String? = null,
    val notifications: List<NotificationItem> = mutableListOf(),
    val isLoading: Boolean = true,
)