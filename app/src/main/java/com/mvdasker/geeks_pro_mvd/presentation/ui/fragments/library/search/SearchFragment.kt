package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.search

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
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSearchBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.LibraryViewModel
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.adapter.NotesAdapterLibrary
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LibraryViewModel by viewModels()

    private var adapter = NotesAdapterLibrary(::onCLick)

    private val noteList: List<Library> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        deleteClearBtn()
        initialize()
        searchCharacterListener()
        crossToLibraryFragment()
        showData()
        binding.etSearch.isSelected = true

    }

    private fun showData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.libraries.collect { libraries ->
                adapter.submitList(libraries)
                Log.e("libraries", "$libraries")
            }
        }
    }

    private fun crossToLibraryFragment() {
        binding.btnCross.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun searchCharacterListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
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
        val filteredList = noteList.filter {
            it.title.contains(query, ignoreCase = true) || it.conspect.contains(
                query,
                ignoreCase = true
            )
        }
        adapter.submitList(filteredList)
    }

    private fun initialize() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
    }

    private fun deleteClearBtn() {
        binding.deleteClearBtn.setOnClickListener {
            if (binding.etSearch.text != null) {
                binding.etSearch.text = null
                binding.etSearch.clearFocus()
            }
        }
    }

    private fun onCLick(model: Library) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                model
            )
        )
    }

}