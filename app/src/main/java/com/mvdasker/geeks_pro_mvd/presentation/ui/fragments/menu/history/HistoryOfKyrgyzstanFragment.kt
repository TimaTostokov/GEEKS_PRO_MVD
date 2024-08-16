package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.history.HistoryResponse
import com.mvdasker.geeks_pro_mvd.databinding.FragmentHistoryOfKyrgyzstanBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.MenuViewModel
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.viewmodel.HistoryViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryOfKyrgyzstanFragment : Fragment() {

    private var _binding: FragmentHistoryOfKyrgyzstanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryOfKyrgyzstanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pk = 1
        viewModel.fetchHistory(pk)

        initListeners()
        observe()

        binding.upBtn.setOnClickListener {
            binding.nestedSv.smoothScrollTo(0, 0)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.history
                .collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.fNotifProgressBar.visible()
                        }
                        is UiState.Error -> {
                            Log.d("tag", "Данные не пришли: ${uiState.message}")
                            binding.tvInfo.text = "Ошибка загрузки данных"
                        }
                        is UiState.Success -> {
                            binding.fNotifProgressBar.gone()
                            val firstItem = uiState.data
                            if (firstItem != null) {
                                binding.tvInfo.text = firstItem.text_ru
                            } else {
                                binding.tvInfo.text = "Нет данных"
                            }
                        }
                    }
                }
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