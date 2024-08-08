package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNotificationsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.adapter.NotificationsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationsViewModel by viewModels()

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

        observeData(viewModel.notification) { uiState ->
            if (uiState.isLoading) {
                onLoading()
            } else if (uiState.error != null) {
                onError()
            } else {
                adapter.submitList(uiState.notifications)
            }
        }
    }

    private fun onLoading() {
    }

    private fun onError() {
    }
}