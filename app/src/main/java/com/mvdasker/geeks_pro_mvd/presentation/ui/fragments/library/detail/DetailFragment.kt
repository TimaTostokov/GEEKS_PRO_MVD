package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        goBack()
        goNotification()
    }

    private fun goBack() {
        binding.ivBellSecond.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun goNotification() {
        binding.flibNotification.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_notificationsFragment)
        }
    }

    private fun initialize() {
        viewModel.getNoteDetail(args.model.id.hashCode())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.noteDetail.collect { note ->
                note?.image?.let { binding.ivPhoto.setImageResource(it) }
                binding.tvTextAsker.text = note?.conspect ?: ""
            }
        }
    }

}