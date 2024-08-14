package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.data.remote.model.library.Library
import com.mvdasker.geeks_pro_mvd.databinding.FragmentLibraryBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.library.adapter.NotesAdapterLibrary
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.snackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.observeData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LibraryViewModel by viewModels()

    private val adapter = NotesAdapterLibrary(::onClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        goToSearch()
        goToNotification()
        observerData()

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    snackbar("Connect to Internet...")
                else -> {
                }
            }
            viewModel.clearMessage()
        }
    }

    private fun observerData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.libraries.collect { libraries ->
                adapter.submitList(libraries)
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

    private fun onClick(model: Library) {
        findNavController().navigate(
            LibraryFragmentDirections.actionLibraryFragmentToDetailFragment(
                model
            )
        )
    }

}