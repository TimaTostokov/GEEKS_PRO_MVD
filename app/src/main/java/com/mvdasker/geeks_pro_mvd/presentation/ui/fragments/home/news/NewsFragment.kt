package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNewsBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.home.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<NewsFragmentArgs>()
    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.id?.let {
            viewModel.setId(it)
        }
        subscribe()
        toBack()
        toNotification()

    }

    private fun subscribe() {
        viewModel.detailState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Error -> Log.e("tag", "нету новостей")
                UiState.Loading -> {}
                is UiState.Success -> {
                    uiState.data.let {
                        Glide.with(binding.ivItem).load(it.image[0].image).into(binding.ivItem)
                        binding.tvNewsTitle.text = it.title
                        binding.tvData.text = it.description
                    }
                }
            }
        }
    }

    private fun toBack() {
        binding.toBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun toNotification() {
        binding.ivIcon.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_notificationsFragment)
        }
    }
}