package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        setupClickListeners()
        openBrowser()
    }

    private fun openBrowser() {
        binding.openDictionaryBtn.setOnClickListener {
            val url = "https://el-sozduk.kg/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.mapBtn.setOnClickListener {
            val url = "https://www.google.com/maps"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setupClickListeners() {
        binding.buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                R.id.kg_btn -> {
                    if (isChecked) {
                        setButtonState(binding.kgBtn, true)
                        setButtonState(binding.ruBtn, false)
                    }
                }
                R.id.ru_btn -> {
                    if (isChecked) {
                        setButtonState(binding.ruBtn, true)
                        setButtonState(binding.kgBtn, false)
                    }
                }
            }
        }
    }

    private fun setButtonState(button: MaterialButton, isSelected: Boolean) {
        if (isSelected) {
            button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        }
    }



    private fun initListeners() {
        binding.apply {
            setOnClickListener(aboutUsButton, MenuFragmentDirections.actionMenuFragmentToAboutUsFragment())
            setOnClickListener(controlKRButton, MenuFragmentDirections.actionMenuFragmentToControlKRFragment())
            setOnClickListener(controlMIAKRButton, MenuFragmentDirections.actionMenuFragmentToControlMIAKRFragment())
            setOnClickListener(controlITMIAKRButton, MenuFragmentDirections.actionMenuFragmentToControlITMIAKRFragment())
            setOnClickListener(trafficRulesButton, MenuFragmentDirections.actionMenuFragmentToTrafficRulesFragment())
        }
    }
    private fun setOnClickListener(button: View, direction: NavDirections) {
        button.setOnClickListener {
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}