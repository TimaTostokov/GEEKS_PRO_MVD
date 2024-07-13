package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.data.remote.model.DataItem
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHomeBinding
import com.mvdasker.geeks_pro_mvd.presenter.ui.adapters.NewsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.UiState

class HomeFragment : Fragment() {

    private val adapter = NewsAdapter(::onClick)
    private val viewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun initialize() {
        binding.rvMain.adapter = adapter
    }

    private fun observe() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { uistate ->
            when (uistate) {
                is UiState.Error -> Log.d("tag", "данные не пришли: ")
                UiState.Loading -> {}
                is UiState.Success -> {
                    adapter.submitList(uistate.data)
                }
            }
        }
    }

    private fun onClick(model: DataItem) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewsFragment(model))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}