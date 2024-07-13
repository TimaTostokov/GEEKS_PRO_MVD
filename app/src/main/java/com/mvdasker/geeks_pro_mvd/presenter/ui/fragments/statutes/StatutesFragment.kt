package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.statutes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentStatutesBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding

class StatutesFragment : Fragment(R.layout.fragment_statutes) {

    private val binding by viewBinding(FragmentStatutesBinding::bind)

    private val adapter = StatuteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fcListStatutes.adapter = adapter
        binding.fcListStatutes.layoutManager = LinearLayoutManager(requireContext())

        binding.fcBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}