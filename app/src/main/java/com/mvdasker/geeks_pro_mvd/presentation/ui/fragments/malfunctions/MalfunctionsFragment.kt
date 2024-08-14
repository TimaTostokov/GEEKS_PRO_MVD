package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMalfunctionsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.showNoInternetSnackbar

class MalfunctionsFragment : Fragment() {

    private var _binding: FragmentMalfunctionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private var isNetworkAvailable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMalfunctionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNetworkCallback()
        binding.btnUpdate.setOnClickListener { handleUpdateButtonClick() }
        checkNetworkAndShowScreen()
        requireContext().showNoInternetSnackbar()
    }

    private fun setupNetworkCallback() {
        connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isNetworkAvailable = true
            }

            override fun onLost(network: Network) {
                isNetworkAvailable = false
                activity?.runOnUiThread { showNoInternetScreen() }
            }
        }
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun handleUpdateButtonClick() {
        if (isNetworkAvailable) {
            findNavController().navigate(R.id.action_techMalfunctionsFragment_to_homeFragment)
        } else {
            requireContext().showNoInternetSnackbar()
        }
    }

    private fun checkNetworkAndShowScreen() {
        isNetworkAvailable = isNetworkAvailable()
        if (!isNetworkAvailable) {
            showNoInternetScreen()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNoInternetScreen() {
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvFetchedData.text = "Сеть недоступна. Убедитесь, что Wi-Fi включен или мобильные данные активны."
        binding.btnUpdate.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        _binding = null
    }

    @SuppressLint("NewApi")
    private fun isNetworkAvailable(): Boolean {
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

}