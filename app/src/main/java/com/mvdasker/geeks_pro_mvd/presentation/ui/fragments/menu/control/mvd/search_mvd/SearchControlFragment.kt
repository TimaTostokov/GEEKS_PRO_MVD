package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.search_mvd

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
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchControlBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.mvd.adapter.ControlMIAKRAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchControlFragment : Fragment(R.layout.fragment_search_control) {

    private var _binding: FragmentSearchControlBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchControlViewModel by viewModels()

    private var adapter = ControlMIAKRAdapter()

    private val controlList: List<Governance> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchControlBinding.bind(view)
        deleteClearBtn()
        initialize()
        crossToSearchControlFragment()
        searchCharacterListener()
        showData()
    }

    private fun showData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.controlIMIAKRA.collect { controls ->
                adapter.submitList(controls)
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
        binding.etSearchMvd.addTextChangedListener(object : TextWatcher {
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
        adapter.updateSearchQuery(query)
        val filteredList = controlList.filter {
            it.jobTittle?.contains(query, ignoreCase = true) == true || it.jobTittle?.contains(
                query,
                ignoreCase = true
            ) == true
        }
        adapter.submitList(filteredList)
    }

    private fun initialize() {
        binding.recyclerViewManagement.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchControlFragment.adapter
        }
    }

    private fun deleteClearBtn() {
        binding.deleteClearBtn.setOnClickListener {
            if (binding.etSearchMvd.text != null) {
                binding.etSearchMvd.text = null
                binding.etSearchMvd.clearFocus()
            }
        }
    }

}