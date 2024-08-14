package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentControlMIAKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.adapter.ControlMIAKRAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ControlMIAKRFragment : Fragment(R.layout.fragment_control_m_i_a_k_r) {

    private val binding by viewBinding(FragmentControlMIAKRBinding::bind)

    private val managementAdapter = ControlMIAKRAdapter()

    private val viewModel: ControlMIAKRViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerData()
        initialize()
        goToSearch()
        goBack()
    }

    private fun observerData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.management.collect { controls ->
                managementAdapter.submitList(controls)
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
            findNavController().navigate(R.id.action_controlMIAKRFragment_to_searchControlFragment)
        }
    }

    private fun goBack() {
        binding.controlBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}