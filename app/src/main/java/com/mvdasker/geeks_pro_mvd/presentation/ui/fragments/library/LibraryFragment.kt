package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.databinding.FragmentLibraryBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.adapter.NotesAdapterLibrary
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.notifications.NotificationsFragment.Companion.NOTIF_ID
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val binding by viewBinding(FragmentLibraryBinding::bind)

    private val viewModel by viewModels<LibraryViewModel>()

    private val adapter = NotesAdapterLibrary(::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        goToSearch()
        goToNotification()
        observerData()
        showBar()
        notificationsAvailability()
    }

    private fun showBar() {
        observeData(viewModel.messageFlow) {
            when (it) {
                is Messages.HideProgressBar ->
                    binding.LibraryProgressBar.gone()

                is Messages.ShowProgressBar ->
                    binding.LibraryProgressBar.visible()

                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun observerData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.libraries.collect { libraries ->
                adapter.submitList(libraries)
                val notifId = arguments?.getInt(NOTIF_ID) ?: 0
                if (notifId > 0) {
                    arguments?.remove(NOTIF_ID)
                    scrollToItemWithId(binding.recyclerView, adapter, notifId)
                }
                Log.e("libraries", "$libraries")
            }
        }
    }

    private fun initialize() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@LibraryFragment.adapter
        }
    }

    private fun goToSearch() {
        binding.etSearch.setOnClickListener {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToSearchFragment())
        }
    }

    private fun goToNotification() {
        binding.ivBell.setOnClickListener {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToNotificationsFragment())
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(
            LibraryFragmentDirections.actionLibraryFragmentToDetailFragment(
                id
            )
        )
    }
    
    private fun notificationsAvailability() {
        observeData(viewModel.notReadNotifCount) {
            if (it != 0) {
                binding.ivBell.setImageResource(R.drawable.bell_not_empty)
            } else {
                binding.ivBell.setImageResource(R.drawable.bell)
            }
        }
    }

    private fun scrollToItemWithId(
        recyclerView: RecyclerView,
        adapter: NotesAdapterLibrary,
        itemId: Int
    ) {
        val position = adapter.getPositionForId(itemId)
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position + 3)
            recyclerView.postDelayed({
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                if (viewHolder is NotesAdapterLibrary.ViewHolder) {
                    viewHolder.highlightItemLibrary()
                }
            }, 300)
        } else {
            Log.e("Scroll", "Элемент с ID $itemId не найден.")
        }
    }

}