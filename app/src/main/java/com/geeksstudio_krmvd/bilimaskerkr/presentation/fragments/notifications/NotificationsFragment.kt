package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentNotificationsBinding
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.notifications.adapter.NotificationsAdapter
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.gone
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.noInternetSnackbar
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.observeData
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.visible
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
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
                        NotificationsFragmentDirections.actionNotificationsFragmentToLibraryFragment(
                            notificationId
                        )
                    )
                }

                "Конституция" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToConstitutionFragment(
                            notificationId
                        )
                    )
                }

                "Закон" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToLawFragment(
                            notificationId
                        )
                    )
                }

                "Устав" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToStatutesFragment(
                            notificationId
                        )
                    )
                }

                "KR Governance" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToControlKRFragment(
                            notificationId
                        )
                    )
                }

                "MVD KR Governance" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToControlMIAKRFragment(
                            notificationId
                        )
                    )
                }

                "VV MVD KR Governance" -> {
                    findNavController().navigate(
                        NotificationsFragmentDirections.actionNotificationsFragmentToControlITMIAKRFragment(
                            notificationId
                        )
                    )
                }

                else -> {
                    Extensions.showToast(requireContext(), getString(R.string.element_not_found))
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

    companion object {
        const val NOTIF_ID = "notif_id"
    }

}