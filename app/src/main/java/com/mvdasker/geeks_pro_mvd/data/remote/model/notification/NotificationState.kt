package com.mvdasker.geeks_pro_mvd.data.remote.model.notification

import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.adapter.NotificationItem

data class NotificationState(
    val error: String? = null,
    val notifications: List<NotificationItem> = mutableListOf(),
    val isLoading: Boolean = false,
)