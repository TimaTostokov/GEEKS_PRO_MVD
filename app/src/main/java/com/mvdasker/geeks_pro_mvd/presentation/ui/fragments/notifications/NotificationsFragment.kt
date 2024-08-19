package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNotificationsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.adapter.NotificationsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NotificationsViewModel>()

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

                else -> {}
            }
            viewModel.clearMessage()
        }
    }

}