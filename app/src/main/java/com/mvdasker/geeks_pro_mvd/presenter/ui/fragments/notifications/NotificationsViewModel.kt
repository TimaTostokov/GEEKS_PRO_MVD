package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvdasker.geeks_pro_mvd.data.repositories.NotificationRepository
import com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.notifications.adapter.NotificationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val notificationRepository: NotificationRepository) :
    ViewModel() {

    private val _notifications: MutableStateFlow<NotificationState> =
        MutableStateFlow(NotificationState())
    val notification: Flow<NotificationState> =
        _notifications.asStateFlow()

    private var currentMonthDate = ""
    private var result = mutableListOf<NotificationItem>()
    private val calendarPrev = Calendar.getInstance()
    private val calendarNext = Calendar.getInstance()
    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    init {
        loadNotificationsList()
    }

    private fun loadNotificationsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listNotifications = notificationRepository.getListNotifications()
                listNotifications.forEach { notification ->
                    if (currentMonthDate.isEmpty()) {
                        val substringBefore = notification.createAt.substringBefore(".")
                        calendarPrev.time = sdf.parse(substringBefore) ?: error("unknown date")
                        currentMonthDate = substringBefore
                        result.add(NotificationItem.MonthItem(calendarPrev.get(Calendar.MONTH)))

                    } else {
                        calendarPrev.time = sdf.parse(currentMonthDate) ?: error("unknown date")

                        calendarNext.time = sdf.parse(notification.createAt.substringBefore("."))
                            ?: error("unknown date")
                        val nexMonth = calendarNext.get(Calendar.MONTH)
                        if (calendarPrev.get(Calendar.MONTH) != nexMonth) {
                            result.add(NotificationItem.MonthItem(nexMonth))
                            currentMonthDate = notification.createAt
                        }
                    }
                    result.add(
                        NotificationItem.Notification(
                            id = notification.id,
                            title = notification.title,
                            description = notification.description,
                            createAt = notification.createAt,
                            isRead = notification.isRead,
                            category = "",
                        )
                    )
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
            }
        }
    }
}


data class NotificationState(
    val error: String? = null,
    val notifications: List<NotificationItem> = mutableListOf(),
    val isLoading: Boolean = true,
)