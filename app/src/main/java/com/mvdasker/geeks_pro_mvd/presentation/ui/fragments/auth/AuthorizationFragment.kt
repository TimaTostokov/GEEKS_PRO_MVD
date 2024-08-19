package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentAuthorizationBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.MenuViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    private val viewModel by viewModels<MenuViewModel>()

    private val viewModelAuth by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            alertDialog()
        }

        onContinueButtonClick()
        setupClickListeners()

        lifecycleScope.launch {
            viewModel.selectedButtonId.collect { selectedId ->
                selectedId?.let { updateButtonState(it) }
            }
        }

        binding.etUserLogin.addTextChangedListener { viewModelAuth.onLoginChanged() }
        binding.etUserPasswords.addTextChangedListener { viewModelAuth.onPasswordChanged() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelAuth.state
                .collect { state ->
                    if (!state.isLoginValid) {
                        binding.tILLogin.setError("Неверный логин")
                    } else {
                        binding.tILLogin.setDefaultState()
                    }
                    if (!state.isPasswordValid) {
                        binding.tILPassword.setErrorState("Неверный пароль")
                    } else {
                        binding.tILPassword.setDefaultState()
                    }
                    if (state.needNavigateToHome) {
                        Extensions.showToast(requireContext(), "Успешная аутентификация!")
                        viewModelAuth.onNavigatedToHome()
                        findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                    }
                }
        }
    }

    private fun updateButtonState(checkedId: Int) {
        setButtonState(binding.kgBtn, checkedId == R.id.kg_btn)
        setButtonState(binding.ruBtn, checkedId == R.id.ru_btn)
    }

    private fun setButtonState(button: MaterialButton, isSelected: Boolean) {
        if (isSelected) {
            button.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            button.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.white)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        }
    }

    private fun setupClickListeners() {
        binding.buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            viewModel.onButtonToggleGroupCheckedChange(checkedId, isChecked)
        }
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.change_language_alert_diaolog, null)
        val buttonKg = view.findViewById<Button>(R.id.dialogButtonKg)
        val buttonRu = view.findViewById<Button>(R.id.dialogButtonRu)
        builder.setView(view)
        buttonKg.setOnClickListener { builder.dismiss() }
        buttonRu.setOnClickListener { builder.dismiss() }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun onContinueButtonClick() {
        binding.btnContinue.setOnClickListener {
            val login = binding.etUserLogin.text.toString()
            val password = binding.etUserPasswords.text.toString()
            viewModelAuth.onLoginClick(login, password)
        }
    }
}

private fun TextInputLayout.setDefaultState() {
    boxStrokeColor = ContextCompat.getColor(context, R.color.dark_blue)
    error = null
}

private fun TextInputLayout.setErrorState(message: String) {
    boxStrokeColor = ContextCompat.getColor(context, R.color.design_default_color_error)
    error = message
}