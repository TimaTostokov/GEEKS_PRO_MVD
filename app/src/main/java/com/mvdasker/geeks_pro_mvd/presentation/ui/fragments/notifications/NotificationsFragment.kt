package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNotificationsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.adapter.NotificationsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val binding by viewBinding(FragmentNotificationsBinding::bind)

    private val viewModel by viewModels<NotificationsViewModel>()

    private val adapter = NotificationsAdapter(
        onNotificationClick = { id, notificationType, notificationId ->
            viewModel.getNotifById(id)
            when (notificationType) {
                "Библиотека" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToDetailFragment(
                            notificationId
                        )
                    )
                }

                "Конституция" -> {
                    findNavController().navigate(R.id.action_notificationsFragment_to_constitutionFragment)

                }

                "Закон" -> {
                    findNavController().navigate(R.id.action_notificationsFragment_to_lawFragment)

                }

                "Устав" -> {
                    findNavController().navigate(R.id.action_notificationsFragment_to_statutesFragment)
                }

                "KR Governance" -> {
                    findNavController().navigate(R.id.action_notificationsFragment_to_controlKRFragment)
                }

                "MVD KR Governance" -> {
                    findNavController().navigate(R.id.action_notificationsFragment_to_controlMIAKRFragment)
                }

                "VV MVD KR Governance" -> {
                    findNavController().navigate(R.id.action_notificationsFragment_to_controlITMIAKRFragment)
                }
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fNotifList.adapter = adapter
        binding.fNotifList.layoutManager = LinearLayoutManager(requireContext())

        binding.fNotifBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fNotifUpBtn.setOnClickListener {
            binding.fNotifList.smoothScrollToPosition(0)
        }

        observeData(viewModel.notification) { notificationState ->
            if (notificationState.isLoading) {
                binding.fNotifProgressBar.visible()
            } else if (notificationState.error != null) {
                error("Loading Error")
            } else {
                adapter.submitList(notificationState.notifications)
                binding.fNotifProgressBar.gone()
            }
        }

        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

}