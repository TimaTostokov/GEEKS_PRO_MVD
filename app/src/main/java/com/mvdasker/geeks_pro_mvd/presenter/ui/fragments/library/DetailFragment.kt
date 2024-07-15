package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
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
    }

    private fun goBack() {
        binding.ivBellSecond.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initialize() {
        binding.ivPhoto.setImageResource(args.model.image)
        binding.tvTextAsker.text = args.model.text.toString()
    }
}