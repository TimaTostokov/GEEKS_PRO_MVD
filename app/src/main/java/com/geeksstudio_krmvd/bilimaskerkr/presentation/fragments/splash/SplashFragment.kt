package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentSplashBinding
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    private val handler = Handler(Looper.getMainLooper())
    private val navigateRunnable = Runnable {
        findNavController().navigate(R.id.action_splashFragment_to_authorizationFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler.postDelayed(navigateRunnable, 1400)

        activity?.window?.let { window ->
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(navigateRunnable)

        activity?.window?.let { window ->
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.dark_blue)
        }
    }

}