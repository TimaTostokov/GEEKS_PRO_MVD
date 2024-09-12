package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHomeBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.adapters.NewsAdapter
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.adapters.NewsLoadingStateAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val adapter = NewsAdapter(::onClick)

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        showSnack()
        notificationsAvailability()

        binding.rvMain.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsLoadingStateAdapter(adapter),
            footer = NewsLoadingStateAdapter(adapter)
        )

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.rvMain.isVisible = state.refresh != LoadState.Loading
            binding.fHomeProgressBar.isVisible = state.refresh == LoadState.Loading

            if (state.refresh is LoadState.Error) {
                noInternetSnackbar()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsPager.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

        binding.fDocUpBtn.setOnClickListener {
            binding.rvMain.smoothScrollToPosition(0)
        }

        binding.fhNotif.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
        }
    }

    private fun initialize() {
        binding.rvMain.adapter = adapter
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun notificationsAvailability() {
        observeData(viewModel.notReadNotifCount) {
            if (it != 0) {
                binding.fhNotif.setImageResource(R.drawable.bell_not_empty)
            } else {
                binding.fhNotif.setImageResource(R.drawable.bell)
            }
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToNewsFragment(id)
        )
    }

}