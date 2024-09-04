package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHomeBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.adapters.NewsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val adapter = NewsAdapter(::onClick)

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        observe()
        showSnack()

        binding.fDocUpBtn.setOnClickListener {
            binding.nestedSv.smoothScrollTo(0, 0)
        }

        binding.fhNotif.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
        }
    }

    private fun initialize() {
        binding.rvMain.adapter = adapter
    }

    private fun observe() {
        observeData(viewModel.newsState) {
            when (it) {
                UiState.Loading -> {
                    binding.fHomeProgressBar.visible()
                }

                is UiState.Success -> {
                    adapter.submitList(it.data)
                    binding.fHomeProgressBar.gone()
                    Log.d("toli", "данные пришли${it.data}")
                }

                is UiState.Error -> {
                    Log.e("toli", "данные не пришли frag")
                    binding.fHomeProgressBar.gone()
                }
            }
        }
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

    /**
     * не удалять
     */
    private fun notificationsAvailability(){
        observeData(viewModel.notReadNotifCount){
            if(it != 0){
               binding.fhNotif.setImageResource(R.drawable.bell_not_empty)
            }else{
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