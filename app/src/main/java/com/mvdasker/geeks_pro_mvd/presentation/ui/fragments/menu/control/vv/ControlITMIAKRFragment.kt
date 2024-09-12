package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentControlITMIAKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv.adapter.ManagementVVAdapter
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ControlITMIAKRFragment : Fragment(R.layout.fragment_control_i_t_m_i_a_k_r) {

    private val binding by viewBinding(FragmentControlITMIAKRBinding::bind)

    private val managementAdapter = ManagementVVAdapter()

    private val viewModel by viewModels<ControlITMIAKRViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerData()
        initialize()
        goToSearch()
        goBack()
        showSnack()
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                is Messages.ShowProgressBar ->
                    binding.fcRukVMKProgressBar.visible()

                is Messages.HideProgressBar ->
                    binding.fcRukVMKProgressBar.gone()
            }
            viewModel.clearMessage()
        }
    }

    private fun observerData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.managementVv.collect { controls ->
                managementAdapter.submitList(controls)
                val notifId = arguments?.getInt(NOTIF_ID) ?: 0
                if (notifId > 0) {
                    arguments?.remove(NOTIF_ID)
                    scrollToItemWithId(binding.rvControllVv, managementAdapter, notifId)
                }
            }
        }
    }

    private fun initialize() {
        binding.rvControllVv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ControlITMIAKRFragment.managementAdapter
        }
    }

    private fun goToSearch() {
        binding.etSearchVv.setOnClickListener {
            findNavController().navigate(ControlITMIAKRFragmentDirections.actionControlITMIAKRFragmentToSearchControlVVFragment())
        }
    }

    private fun goBack() {
        binding.controlBackBtnVv.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun scrollToItemWithId(
        recyclerView: RecyclerView,
        adapter: ManagementVVAdapter,
        itemId: Int
    ) {
        val position = adapter.getPositionForId(itemId)
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position + 2)
            recyclerView.postDelayed({
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                if (viewHolder is ManagementVVAdapter.ManagementsKgViewHolder) {
                    viewHolder.highlightItemControlVV()
                }
            }, 300)
        } else {
            Log.e("Scroll", "Элемент с ID $itemId не найден.")
        }
    }

}