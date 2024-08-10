package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.mangements.Governance
import com.mvdasker.geeks_pro_mvd.databinding.FragmentControlITMIAKRBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv.adapter.ManagementVVAdapter
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.control.vv.viewmodel.ControlITMIAKRViewModel
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ControlITMIAKRFragment : Fragment(R.layout.fragment_control_i_t_m_i_a_k_r) {

    private val binding by viewBinding(FragmentControlITMIAKRBinding::bind)

    private val managementAdapter = ManagementVVAdapter()

    private val viewModel: ControlITMIAKRViewModel by viewModels()

    private var originalList: List<Governance> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
//        subscribe()
        searchCharacterListener()
        goBack()
        deleteClearBtn()
    }

    private fun initialize() {
        binding.rvControll.adapter = managementAdapter
    }

    private fun setupListeners() {
        observeData(viewModel.managementState) { state ->
            when (state) {
                is UiState.Error -> Log.e("management", "данные не получены: ")
                UiState.Loading -> {
                }

                is UiState.Success -> {
                    Log.d("tag", "данные получены: ${state.data}")
                    originalList = state.data // Сохранение оригинального списка
                    managementAdapter.submitList(originalList)
                }
            }
        }
    }

//    private fun subscribe() {
//        viewModel.sinigamiLiveData.observe(viewLifecycleOwner) { uiState ->
//            uiState?.let {
//                if (!it.isLoading) {
//                    if (it.success != null) {
//                        Log.e("tag", "subscribe:${it.success}")
//                        originalList = it.success
//                        managementAdapter.submitList(originalList)
//                    } else {
//                        Log.e("tag", "error:${it}")
//                    }
//                }
//            }
//        }
//    }

    private fun searchCharacterListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    searchCharacter(s.toString())
                    managementAdapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchCharacter(query: String) {
        managementAdapter.updateSearchQuery(query)
        val filteredList = originalList.filter {
            it.category?.contains(query, ignoreCase = true) == true || it.name?.contains(
                query,
                ignoreCase = true
            ) == true
        }
        managementAdapter.submitList(filteredList)
    }

    private fun goBack() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun deleteClearBtn() {
        binding.deleteClearBtn.setOnClickListener {
            if (binding.etSearch.text != null) {
                binding.etSearch.text = null
                binding.etSearch.clearFocus()
                managementAdapter.submitList(originalList) // Восстановление оригинального списка
            }
        }
    }

}