package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.Notification
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.NotificationItem
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.NotificationState
import com.mvdasker.geeks_pro_mvd.data.repositories.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notifications: MutableStateFlow<NotificationState> =
        MutableStateFlow(NotificationState())
    val notification: Flow<NotificationState> =
        _notifications.asStateFlow()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    private var currentMonthDate = ""
    private var result = mutableListOf<NotificationItem>()
    private val calendarPrev = Calendar.getInstance()
    private val calendarNext = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    init {
        loadNotificationsList()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    private fun loadNotificationsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listNotifications = notificationRepository.getListNotifications()
                listNotifications.forEach { notification ->
                    if (validateNotif(notification)) {
                        if (currentMonthDate.isEmpty()) {
                            val substringBefore = notification.createAt?.substringBefore(".")
                            calendarPrev.time =
                                substringBefore?.let { sdf.parse(it) } ?: error("unknown date")
                            currentMonthDate = substringBefore
                            result.add(NotificationItem.MonthItem(calendarPrev.get(Calendar.MONTH)))

                        } else {
                            calendarPrev.time = sdf.parse(currentMonthDate) ?: error("unknown date")

                            calendarNext.time =
                                sdf.parse(notification.createAt?.substringBefore(".") ?: "")
                                    ?: error("unknown date")
                            val nexMonth = calendarNext.get(Calendar.MONTH)
                            if (calendarPrev.get(Calendar.MONTH) != nexMonth) {
                                result.add(NotificationItem.MonthItem(nexMonth))
                                currentMonthDate = notification.createAt.toString()
                            }
                        }
                        result.add(
                            NotificationItem.Notification(
                                id = notification.id,
                                section = notification.section,
                                title = notification.title,
                                createAt = notification.createAt,
                                readed = notification.readed,
                                notificationId = notification.notificationId,
                                notificationType = notification.notificationType
                            )
                        )
                    }
                }
                _notifications.update {
                    val not = it.notifications.toMutableList()
                    not.addAll(result)
                    it.copy(
                        isLoading = false,
                        notifications = not
                    )
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                _notifications.update {
                    it.copy(error = "")
                }
                _messageFlow.value = Messages.NetworkIsDisconnected
            }
        }
    }

    private fun validateNotif(notif: Notification): Boolean = notif.createAt != null

    fun getNotifById(notifId: Int) {
        viewModelScope.launch {
            val notifIdx =
                _notifications.value.notifications.indexOfFirst { it is NotificationItem.Notification && it.id == notifId }
            if (notifIdx >= 0) {
                _notifications.update { state ->
                    val notifications = state.notifications.toMutableList()
                    notifications[notifIdx] =
                        (notifications[notifIdx] as NotificationItem.Notification).copy(
                            readed = true,
                        )
                    state.copy(
                        notifications = notifications
                    )
                }
            }
            notificationRepository.getNotificationById(notifId)
        }
    }

}