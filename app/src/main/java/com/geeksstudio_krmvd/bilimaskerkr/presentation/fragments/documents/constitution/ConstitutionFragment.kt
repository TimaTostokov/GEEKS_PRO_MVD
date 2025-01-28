package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.common.Messages
import com.geeksstudio_krmvd.bilimaskerkr.common.UiState
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentConstitutionBinding
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.documents.constitution.adapter.ConstitutionsAdapter
import com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.gone
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.noInternetSnackbar
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.observeData
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.Extensions.visible
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionFragment : Fragment(R.layout.fragment_constitution) {

    private val binding by viewBinding(FragmentConstitutionBinding::bind)

    private val viewModel by viewModels<ConstitutionsViewModel>()

    private val adapter = ConstitutionsAdapter(::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupUI()
        observeViewModel()
        snackBar()
        toGoSearch()
    }

    private fun initialize() {
        binding.rvConstitution.adapter = adapter
        binding.rvConstitution.setHasFixedSize(true)
        binding.rvConstitution.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupUI() {
        binding.fconstBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        observeData(viewModel.constitution) { uiState ->
            when (uiState) {
                is UiState.Loading -> binding.fcLawProgressBar.visible()
                is UiState.Success -> {
                    binding.fcLawProgressBar.gone()
                    adapter.setFilteredList(
                        mList = uiState.data,
                        query = ""
                    )
                    val notifId = arguments?.getInt(NOTIF_ID) ?: 0
                    if (notifId > 0) {
                        arguments?.remove(NOTIF_ID)
                        scrollToItemWithId(binding.rvConstitution, adapter, notifId)
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
            findNavController().navigate(R.id.action_constitutionFragment_to_constitutionsSearchFragment)
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(
            ConstitutionFragmentDirections.actionConstitutionFragmentToConstitutionsDetailFragment(
                id
            )
        )
    }

    private fun scrollToItemWithId(
        recyclerView: RecyclerView,
        adapter: ConstitutionsAdapter,
        itemId: Int,
    ) {
        val position = adapter.getPositionForId(itemId)
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position + 3)
            recyclerView.nestedScrollBy(0, position)
            recyclerView.postDelayed({
                adapter.highlightItemConstitutionAtPosition(position)
            }, 300)
        } else {
            Log.e("Scroll", "Элемент с ID $itemId не найден.")
        }
    }

    private fun snackBar() {
        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                    binding.fcLawProgressBar.visible()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
        }
    }

}