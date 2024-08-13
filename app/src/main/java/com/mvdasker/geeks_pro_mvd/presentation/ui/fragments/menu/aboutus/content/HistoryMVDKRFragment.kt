package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.aboutus.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentAboutUsBinding
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHistoryMVDKRBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryMVDKRFragment : Fragment() {

    private var _binding: FragmentHistoryMVDKRBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryMVDKRBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

        binding.upBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.upBtn.setOnClickListener {
            binding.nestedSv.smoothScrollTo(0, 0)
        }
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}