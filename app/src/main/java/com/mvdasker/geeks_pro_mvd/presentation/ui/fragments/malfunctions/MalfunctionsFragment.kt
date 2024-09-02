package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMalfunctionsBinding

class MalfunctionsFragment : Fragment() {

    private var _binding: FragmentMalfunctionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ServerStatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMalfunctionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.serverStatus.observe(viewLifecycleOwner) { status ->
            handleServerStatus(status)
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.startCheckingServerStatus()
            Snackbar.make(binding.root, getString(R.string.checking_server), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startCheckingServerStatus()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopCheckingServerStatus()
    }

    private fun handleServerStatus(status: ServerStatus) {
        val message = when(status) {
            ServerStatus.AVAILABLE -> getString(R.string.server_ok)
            ServerStatus.UNAVAILABLE -> getString(R.string.server_error)
            ServerStatus.NO_INTERNET -> getString(R.string.no_internet_message)
        }

        if (message.isNotEmpty()) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }

        val isUnavailable = status == ServerStatus.UNAVAILABLE
        binding.tvErrorMessage.isVisible = isUnavailable
        binding.btnUpdate.isVisible = isUnavailable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}