package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.control.kg

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentControlKRBinding
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.control.kg.adapter.ControlKgAdapter
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.gone
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.noInternetSnackbar
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.observeData
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.visible
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ControlKRFragment : Fragment(R.layout.fragment_control_k_r) {

    private val binding by viewBinding(FragmentControlKRBinding::bind)

    private val managementAdapter = ControlKgAdapter()

    private val viewModel by viewModels<ControlKgVIewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupListeners()
        goBack()
        showSnack()
    }

    private fun initialize() {
        binding.rvControll.adapter = managementAdapter
    }

    private fun setupListeners() {
        observeData(viewModel.managementState) { state ->
            when (state) {
                is UiState.Error -> {
                    Log.e("management", "данные не получены: ")
                    binding.fcRukKrProgressBar.gone()
                }

                UiState.Loading -> {
                    binding.fcRukKrProgressBar.visible()
                }

                is UiState.Success -> {
                    managementAdapter.submitList(state.data)
                    binding.fcRukKrProgressBar.gone()
                    val notifId = arguments?.getInt(NOTIF_ID) ?: 0
                    if (notifId > 0) {
                        arguments?.remove(NOTIF_ID)
                        scrollToItemWithId(binding.rvControll, managementAdapter, notifId)
                    }
                }
            }
        }
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                is Messages.ShowProgressBar ->
                    binding.fcRukKrProgressBar.visible()

                is Messages.HideProgressBar ->
                    binding.fcRukKrProgressBar.gone()
            }
            viewModel.clearMessage()
        }
    }

    private fun goBack() {
        binding.vvKRBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun scrollToItemWithId(
        recyclerView: RecyclerView,
        adapter: ControlKgAdapter,
        itemId: Int
    ) {
        val position = adapter.getPositionForId(itemId)
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position + 2)
            recyclerView.postDelayed({
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                if (viewHolder is ControlKgAdapter.ManagementsKgViewHolder) {
                    viewHolder.highlightItemControl()
                }
            }, 300)
        } else {
            Log.e("Scroll", "Элемент с ID $itemId не найден.")
        }
    }

}