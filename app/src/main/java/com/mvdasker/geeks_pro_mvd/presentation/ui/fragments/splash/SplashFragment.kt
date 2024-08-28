package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentSplashBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_authorizationFragment)
        }, 1400)

        activity?.window?.let { window ->
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.let { window ->
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.dark_blue)
        }
    }

}