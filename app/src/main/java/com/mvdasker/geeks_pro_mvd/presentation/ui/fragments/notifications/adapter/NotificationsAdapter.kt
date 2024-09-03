package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.notification.NotificationItem
import com.mvdasker.geeks_pro_mvd.databinding.ItemNotificationBinding
import com.mvdasker.geeks_pro_mvd.databinding.ItemNotificationMonthBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.formatDate
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible

class NotificationsAdapter(private val onNotificationClick: (String?, Int) -> Unit) :
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
            R.layout.item_notification -> NotificationViewHolder(onNotificationClick, v)
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
                        && oldItem.month == newItem.month

                val notificationsItem = oldItem is NotificationItem.Notification
                        && newItem is NotificationItem.Notification
                        && oldItem.title == newItem.title
                        && oldItem.description == newItem.description
                        && oldItem.createAt == newItem.createAt
                        && oldItem.isRead == newItem.isRead
                        && oldItem.section == newItem.section

                return monthItem || notificationsItem
            }
        }
    }
}

class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemNotificationMonthBinding.bind(itemView)
    private val stringArray = itemView.context.resources.getStringArray(R.array.months)

    fun bindMonth(month: NotificationItem.MonthItem) {
        binding.itemMonth.text = stringArray.getOrNull(month.month)
    }
}

class NotificationViewHolder(
    private val onNotificationClick: (String?, Int) -> Unit,
    itemView: View
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ItemNotificationBinding.bind(itemView)

    @SuppressLint("ObsoleteSdkInt")
    fun bindNotification(notification: NotificationItem.Notification) {
        binding.itemNotifDate.text = notification.createAt?.let { formatDate(it) }
        binding.itemNotifTitle.text = notification.title

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.itemNotifDescription.text =
                Html.fromHtml(notification.description, Html.FROM_HTML_MODE_LEGACY)
        }

        if (notification.section != null) {
            binding.itemNotifSection.text = notification.section
            binding.itemNotifSection.visible()
        } else
            binding.itemNotifSection.gone()

        binding.itemNotifNotReadCircle.isVisible = !notification.isRead

        binding.itemCardNotif.setOnClickListener {
            onNotificationClick(notification.section, 0)
        }
    }

}