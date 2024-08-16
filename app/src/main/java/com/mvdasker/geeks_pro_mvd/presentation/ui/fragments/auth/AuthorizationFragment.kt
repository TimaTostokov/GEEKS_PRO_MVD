package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentAuthorizationBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.MenuViewModel
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.showToast
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    private val viewModel: MenuViewModel by viewModels()

    private val viewModelAuth: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelAuth.putLogin(
            username = binding.etUserLogin.toString(),
            password = binding.etUserPasswords.toString()
        )

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

        binding.etUserLogin.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tILLogin.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
                binding.tILLogin.error = null
            }
        }

        binding.etUserPasswords.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tILPassword.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
                binding.tILPassword.error = null
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
        buttonKg.setOnClickListener {
            builder.dismiss()
        }
        buttonRu.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun onContinueButtonClick() {
        binding.btnContinue.setOnClickListener {
            val login = binding.etUserLogin.text.toString()
            val password = binding.etUserPasswords.text.toString()

            if (validateInput(login, password)) {
                findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
            }
        }
    }

    private fun validateInput(login: String, password: String): Boolean {
        val loginPattern = Pattern.compile(getString(R.string.a_za_z0_9_6))
        val passwordPattern = Pattern.compile(getString(R.string.a_za_z0_9_6_))

        return when {
            login.isEmpty() -> {
                showToast(requireContext(), "Login must not be empty")
                binding.tILLogin.error = "Неверный логин"
                false
            }

            password.isEmpty() -> {
                showToast(requireContext(), "Password must not be empty")
                binding.tILPassword.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.red)
                binding.tILPassword.error = "Неверный пароль"
                false
            }

            !loginPattern.matcher(login).matches() -> {
                binding.tILLogin.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.red);
                binding.tILLogin.error = "Неверный логин"
                false
            }

            !passwordPattern.matcher(password).matches() -> {
                binding.tILPassword.error = "Неверный пароль"
                binding.tILPassword.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.red);
                false
            }

            else -> true
        }
    }

}