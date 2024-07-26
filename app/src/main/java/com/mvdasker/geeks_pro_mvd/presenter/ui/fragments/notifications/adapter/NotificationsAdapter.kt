package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.ItemNotificationBinding
import com.mvdasker.geeks_pro_mvd.databinding.ItemNotificationMonthBinding

class NotificationsAdapter() :
    ListAdapter<NotificationItem, RecyclerView.ViewHolder>(NotificationDiffCallback) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is NotificationItem.MonthItem -> R.layout.item_notification_month
        is NotificationItem.Notification -> R.layout.item_notification
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_notification_month -> MonthViewHolder(v)
            R.layout.item_notification -> NotificationViewHolder(v)
            else -> error("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MonthViewHolder -> holder.bindMonth(item as NotificationItem.MonthItem)
            is NotificationViewHolder -> holder.bindNotification(item as NotificationItem.Notification)
        }
    }

    companion object {
        object NotificationDiffCallback : DiffUtil.ItemCallback<NotificationItem>() {

            override fun areItemsTheSame(
                oldItem: NotificationItem,
                newItem: NotificationItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: NotificationItem,
                newItem: NotificationItem
            ): Boolean {
                val monthItem = oldItem is NotificationItem.MonthItem
                        && newItem is NotificationItem.MonthItem
                        && oldItem.title == newItem.title

                val notificationsItem = oldItem is NotificationItem.Notification
                        && newItem is NotificationItem.Notification
                        && oldItem.title == newItem.title
                        && oldItem.description == newItem.description
                        && oldItem.createAt == newItem.createAt
                        && oldItem.isRead == newItem.isRead
                        && oldItem.category == newItem.category

                return monthItem || notificationsItem
            }
        }
    }
}

class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemNotificationMonthBinding.bind(itemView)

    fun bindMonth(month: NotificationItem.MonthItem) {
        binding.itemMonth.text = month.title
    }
}

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemNotificationBinding.bind(itemView)

    fun bindNotification(notification: NotificationItem.Notification) {
        binding.itemNotifDate.text = notification.createAt
        binding.itemNotifTitle.text = notification.title
        binding.itemNotifDescription.text = notification.description
        binding.itemNotifCategory.text = notification.category
        binding.itemNotifNotReadCircle.isVisible = !notification.isRead

    }
}

sealed interface NotificationItem {
    data class MonthItem(val title: String) : NotificationItem
    data class Notification(
        val id: Int,
        val title: String,
        val description: String,
        val createAt: String,
        val isRead: Boolean,
        val category: String
    ) : NotificationItem
}