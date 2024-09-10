package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentLawBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.adapter.LawAdapter
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LawFragment : Fragment(R.layout.fragment_law) {
    private val binding by viewBinding(FragmentLawBinding::bind)
    private val viewModel by viewModels<LawViewModel>()
    private val adapter = LawAdapter(::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupUI()
        observeViewModel()
        toGoSearch()
    }

    private fun initialize() {
        binding.rvLaws.adapter = adapter
        binding.rvLaws.setHasFixedSize(true)
        binding.rvLaws.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun setupUI() {
        binding.flBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        observeData(viewModel.law) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.fcLawProgressBar.visible()
                is UiState.Success -> {
                    binding.fcLawProgressBar.gone()
                    adapter.setFilteredList(
                        mList = uiState.data,
                        query = "",
                    )
                    val notifId = arguments?.getInt(NOTIF_ID) ?: 0
                    if (notifId > 0) {
                        arguments?.remove(NOTIF_ID)
                        scrollToItemWithId(binding.rvLaws, adapter, notifId)
                    }
                }

                is UiState.Error -> {
                    binding.fcLawProgressBar.gone()
                    Log.e("LawFragment", "Ошибка получения данных")
                }
            }
        }

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected -> noInternetSnackbar()
                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
        }
    }

    private fun toGoSearch() {
        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_lawFragment_to_searchLawsFragment)
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(LawFragmentDirections.actionLawFragmentToDetailLawsFragment(id))
    }

    private fun scrollToItemWithId(
        recyclerView: RecyclerView,
        adapter: LawAdapter,
        itemId: Int
    ) {
        val position = adapter.getPositionForId(itemId)
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position + 3)
            recyclerView.postDelayed({
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                if (viewHolder is LawAdapter.LawViewHolder) {
                    viewHolder.highlightItemLaw()
                }
            }, 300)
        } else {
            Log.e("Scroll", "Элемент с ID $itemId не найден.")
        }
    }
}