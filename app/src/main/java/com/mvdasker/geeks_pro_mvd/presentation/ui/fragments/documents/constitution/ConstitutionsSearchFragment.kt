package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.Constitutions
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionsSearchBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.adapter.ConstitutionsAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionsSearchFragment : Fragment(R.layout.fragment_constitutions_search) {

    private val binding by viewBinding(FragmentConstitutionsSearchBinding::bind)

    private val viewModel by viewModels<ConstitutionsViewModel>()

    private val adapter by lazy {
        ConstitutionsAdapter(mList,::onCLick)
    }

    private var mList = ArrayList<Constitutions>()

    private var controlList: List<Constitutions> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        searchCharacterListener()
        crossToLibraryFragment()
        showData()
        showShak()
        deleteClearBtn()
        binding.etSearch.isSelected = true
    }

    private fun showData() {
        observeData(viewModel.constitution) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.progressBar.visible()
                is UiState.Success -> {
                    binding.progressBar.gone()
                    adapter.setFilteredList(uiState.data)
                    controlList = uiState.data
                }

                is UiState.Error -> {
                    binding.progressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных")
                }
            }
        }
    }

    private fun showShak() {
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    noInternetSnackbar()

                is Messages.HideProgressBar ->
                    binding.progressBar.gone()

                is Messages.ShowProgressBar ->
                    binding.progressBar.visible()
            }
            viewModel.clearMessage()
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
        val filteredList = controlList.filter {
            it.section?.contains(query, ignoreCase = true) ?: false ||
                    it.section?.contains(query, ignoreCase = true) ?: false
        }
        adapter.setFilteredList(filteredList)
    }


    private fun initialize() {
        binding.rvSearchConstitution.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ConstitutionsSearchFragment.adapter
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


    private fun onCLick(id: Int) {
        findNavController().navigate(
            ConstitutionsSearchFragmentDirections.actionConstitutionsSearchFragmentToConstitutionsDetailFragment(
                id
            )
        )
    }
}

