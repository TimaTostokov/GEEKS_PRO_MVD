package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv.search_vv

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchControlVVBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.adapter.ControlMIAKRAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchControlVVFragment : Fragment(R.layout.fragment_search_control_v_v) {

    private val binding by viewBinding(FragmentSearchControlVVBinding::bind)

    private val viewModel: SearchControlVVViewModel by viewModels()

    private var adapterVV = ControlMIAKRAdapter()

    private val controlVVList: List<Governance> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteClearBtn()
        initialize()
        crossToSearchControlFragment()
        searchCharacterListener()
        showData()
    }

    private fun showData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.controlITMIAKR.collect { controls ->
                adapterVV.submitList(controls)
                Log.e("control", "$controls")
            }
        }
    }

    private fun crossToSearchControlFragment() {
        binding.btnCross.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun searchCharacterListener() {
        binding.etSearchVv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let {
                    searchCharacter(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchCharacter(query: String) {
        adapterVV.updateSearchQuery(query)
        val filteredList = controlVVList.filter {
            it.jobTittle?.contains(query, ignoreCase = true) == true || it.jobTittle?.contains(
                query,
                ignoreCase = true
            ) == true
        }
        adapterVV.submitList(filteredList)
    }

    private fun initialize() {
        binding.recyclerViewManagementVv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchControlVVFragment.adapterVV
        }
    }

    private fun deleteClearBtn() {
        binding.deleteClearBtnVv.setOnClickListener {
            if (binding.etSearchVv.text != null) {
                binding.etSearchVv.text = null
                binding.etSearchVv.clearFocus()
            }
        }
    }

}