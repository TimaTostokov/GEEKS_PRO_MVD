package com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.notification

data class NotificationState(
    val error: String? = null,
    val notifications: List<NotificationItem> = mutableListOf(),
    val isLoading: Boolean = true
)