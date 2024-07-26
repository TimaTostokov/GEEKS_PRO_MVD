package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNotificationsBinding
import com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.notifications.adapter.NotificationItem
import com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.notifications.adapter.NotificationsAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val adapter = NotificationsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fNotifList.adapter = adapter
        binding.fNotifList.layoutManager = LinearLayoutManager(requireContext())

        setupList()

        binding.fNotifBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fNotifUpBtn.setOnClickListener {
            binding.fNotifList.smoothScrollToPosition(0)
        }
    }

    private fun setupList() {
        val notifList = listOf(
            NotificationItem.MonthItem("Июль"),
            NotificationItem.Notification(
                id = 1,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 июля 2024",
                isRead = false,
                category = "Библиотека"
            ),
            NotificationItem.Notification(
                id = 2,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 июля 2024",
                isRead = false,
                category = "Библиотека"
            ),
            NotificationItem.Notification(
                id = 3,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 июля 2024",
                isRead = true,
                category = "Документы"
            ),
            NotificationItem.MonthItem("Июнь"),

            NotificationItem.Notification(
                id = 4,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 июня 2024",
                isRead = true,
                category = "Библиотека"
            ),
            NotificationItem.Notification(
                id = 5,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 июня 2024",
                isRead = true,
                category = "Библиотека"
            ),
            NotificationItem.Notification(
                id = 6,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 июня 2024",
                isRead = true,
                category = "Документы"
            ),
            NotificationItem.MonthItem("Mай"),

            NotificationItem.Notification(
                id = 7,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 мая 2024",
                isRead = true,
                category = "Библиотека"
            ),
            NotificationItem.Notification(
                id = 8,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 мая 2024",
                isRead = true,
                category = "Библиотека"
            ),
            NotificationItem.Notification(
                id = 9,
                title = "Давайте разбираться вместе: жизнь прекрасна",
                description = "Но постоянный количественный рост и сфера нашей активности способстует подготовке и реализации распределения внутренних резервов и ресурсов",
                createAt = "24 мая 2024",
                isRead = true,
                category = "Документы"
            ),
        )
        adapter.submitList(notifList)
    }
}