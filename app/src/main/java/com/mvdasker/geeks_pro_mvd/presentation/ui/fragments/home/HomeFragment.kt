package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHomeBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.adapters.NewsAdapter
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.viewmodel.HomeViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter = NewsAdapter(::onClick)

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        observe()
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
                is UiState.Error -> {
                    Log.e("tag", "данные не пришли frag")
                }

                UiState.Loading -> {}

                is UiState.Success -> {
                    adapter.submitList(it.data)
                    Log.d("tag", "данные пришли")
                }
            }
        }
    }

    private fun onClick(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToNewsFragment(id)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}