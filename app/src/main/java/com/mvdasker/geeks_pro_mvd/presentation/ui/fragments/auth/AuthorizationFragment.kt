package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.auth

import android.os.Bundle
import android.util.Log
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
import java.util.Locale

@AndroidEntryPoint
class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    private val viewModel by viewModels<MenuViewModel>()

    private val viewModelAuth by viewModels<AuthViewModel>()

    private var isShowDialog: Boolean = true

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ALERT_DIALOG_KEY, isShowDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isShowDialog = savedInstanceState.getBoolean(ALERT_DIALOG_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog()
        onContinueButtonClick()
        setupClickListeners()
        loadSavedLanguage()

        binding.etUserLogin.addTextChangedListener { viewModelAuth.onLoginChanged() }
        binding.etUserPasswords.addTextChangedListener { viewModelAuth.onPasswordChanged() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModelAuth.state.collect { state ->
                binding.tILLogin.apply {
                    if (state.isLoginValid) setDefaultState() else setError("Неверный логин")
                }
                binding.tILPassword.apply {
                    if (state.isPasswordValid) setDefaultState() else setErrorState("Неверный пароль")
                }
                if (state.needNavigateToHome) {
                    Log.e("ololo", "Authorization failed: ${state.user}")
                    Extensions.showToast(requireContext(), "Успешная аутентификация!")
                    viewModelAuth.onNavigatedToHome()
                    findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                }
            }
        }
    }

    private fun updateButtonState(languageCode: String) {
        val kgBtnSelected = languageCode == "ky"
        binding.kgBtn.updateState(kgBtnSelected)
        binding.ruBtn.updateState(!kgBtnSelected)
    }

    private fun MaterialButton.updateState(isSelected: Boolean) {
        backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            if (isSelected) R.color.dark_blue else R.color.white
        )
        setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isSelected) R.color.white else R.color.dark_blue
            )
        )
    }

    private fun setupClickListeners() {
        binding.apply {
            kgBtn.setOnClickListener {
                updateLocale("ky")
            }

            ruBtn.setOnClickListener {
                updateLocale("ru")
            }
        }
    }

    private fun alertDialog() {
        if (!isShowDialog) return
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.change_language_alert_diaolog, null)
        val buttonKg = view.findViewById<Button>(R.id.dialogButtonKg)
        val buttonRu = view.findViewById<Button>(R.id.dialogButtonRu)
        builder.setView(view)
        buttonKg.setOnClickListener {
            builder.dismiss()
            isShowDialog = false
        }
        buttonRu.setOnClickListener {
            builder.dismiss()
            isShowDialog = false
        }
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

    private fun TextInputLayout.setDefaultState() {
        boxStrokeColor = ContextCompat.getColor(context, R.color.dark_blue)
        error = null
    }

    private fun TextInputLayout.setErrorState(message: String) {
        boxStrokeColor = ContextCompat.getColor(context, R.color.design_default_color_error)
        error = message
    }

    private fun loadSavedLanguage() {
        val savedLanguage = viewModel.getSavedLanguage()
        updateButtonState(savedLanguage)
        if (resources.configuration.locales[0].language != savedLanguage) {
            updateLocale(savedLanguage)
        }
    }

    private fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode.lowercase(Locale.ROOT))

        val config = resources.configuration.apply {
            setLocale(locale)
        }

        val intent = requireActivity().intent
        requireActivity().apply {
            viewModel.saveSelectedLanguage(languageCode)
            recreate()
        }
    }

    companion object {
        const val ALERT_DIALOG_KEY = "alertDialog"
    }

}