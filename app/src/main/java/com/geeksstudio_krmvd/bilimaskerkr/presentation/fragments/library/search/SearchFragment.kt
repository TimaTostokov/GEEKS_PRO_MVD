package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.library.search

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.data.remote.model.library.Library
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentSearchBinding
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.library.LibraryViewModel
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.library.adapter.NotesAdapterLibrary
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.gone
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.noInternetSnackbar
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.observeData
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.visible
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<LibraryViewModel>()

    private var adapter = NotesAdapterLibrary(::onCLick)

    private var noteList: List<Library> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteClearBtn()
        initialize()
        searchCharacterListener()
        crossToLibraryFragment()
        showData()
        showSnack()
        binding.etSearch.isSelected = true
    }

    private fun showSnack() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                is Messages.HideProgressBar ->
                    binding.fDetalProgressBar.gone()

                is Messages.ShowProgressBar ->
                    binding.fDetalProgressBar.visible()
            }
            viewModel.clearMessage()
        }
    }

    private fun showData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.libraries.collect { libraries ->
                adapter.submitList(libraries)
                Log.e("libraries", "$libraries")
                noteList = libraries
                updateItemCount(libraries.size)
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
            it.title?.contains(query, ignoreCase = true) ?: false ||
                    it.conspect?.contains(query, ignoreCase = true) ?: false
        }
        adapter.submitList(filteredList)
        updateItemCount(filteredList.size)
    }

    private fun updateItemCount(count: Int) {
        val spannableString = SpannableString(count.toString())
        val color = ContextCompat.getColor(binding.root.context, R.color.search_color)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.number2.text = spannableString
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
                adapter.submitList(noteList)
                updateItemCount(noteList.size)
            }
        }
    }

    private fun onCLick(id: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                id
            )
        )
    }

}