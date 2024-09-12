package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd

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
import com.mvdasker.geeks_pro_mvd.databinding.FragmentControlMIAKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.adapter.ControlMIAKRAdapter
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ControlMIAKRFragment : Fragment(R.layout.fragment_control_m_i_a_k_r) {

    private val binding by viewBinding(FragmentControlMIAKRBinding::bind)

    private val managementAdapter = ControlMIAKRAdapter()

    private val viewModel by viewModels<ControlMIAKRViewModel>()

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
                    binding.fcRukMVDMKProgressBar.visible()

                is Messages.HideProgressBar ->
                    binding.fcRukMVDMKProgressBar.gone()
            }
            viewModel.clearMessage()
        }
    }

    private fun observerData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.management.collect { controls ->
                managementAdapter.submitList(controls)
                val notifId = arguments?.getInt(NOTIF_ID) ?: 0
                if (notifId > 0) {
                    arguments?.remove(NOTIF_ID)
                    scrollToItemWithId(binding.rvControll, managementAdapter, notifId)
                }
                Log.e("controls", "$controls")
            }
        }
    }

    private fun initialize() {
        binding.rvControll.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ControlMIAKRFragment.managementAdapter
        }
    }

    private fun goToSearch() {
        binding.etSearch.setOnClickListener {
            findNavController().navigate(ControlMIAKRFragmentDirections.actionControlMIAKRFragmentToSearchControlFragment())
        }
    }

    private fun goBack() {
        binding.controlBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun scrollToItemWithId(
        recyclerView: RecyclerView,
        adapter: ControlMIAKRAdapter,
        itemId: Int
    ) {
        val position = adapter.getPositionForId(itemId)
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position + 2)
            recyclerView.postDelayed({
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                if (viewHolder is ControlMIAKRAdapter.ManagmentsKgViewHolder) {
                    viewHolder.highlightItemControlMIAKR()
                }
            }, 300)
        } else {
            Log.e("Scroll", "Элемент с ID $itemId не найден.")
        }
    }

}