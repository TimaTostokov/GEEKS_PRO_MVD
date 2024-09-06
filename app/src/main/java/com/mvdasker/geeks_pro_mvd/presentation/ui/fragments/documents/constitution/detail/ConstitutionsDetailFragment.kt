package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.constitution.ConstitutionsChapter
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionsDetailBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionsDetailFragment : Fragment(R.layout.fragment_constitutions_detail) {

    private val viewModel by viewModels<ConstitutionsDetailViewModel>()
    private val binding by viewBinding(FragmentConstitutionsDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id") ?: return

        viewModel.loadConstitutionById(id)

        observeViewModel()
    }

    private fun observeViewModel() {
        observeData(viewModel.constitutionDetail) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.progressBar.visible()
                is UiState.Success -> {
                    binding.progressBar.gone()
                    displayData(uiState.data)
                }

                is UiState.Error -> {
                    binding.progressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных")
                }
            }
        }
    }

    private fun displayData(data: ConstitutionsChapter) {
        binding.tvChapterSearch.text = data.chapter
        binding.tvConstSearch.text = data.article
    }
}

}