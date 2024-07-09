package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentAuthorizationBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.showToast
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import java.util.regex.Pattern

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onContinueButtonClick()
    }

    private fun onContinueButtonClick() {
        binding.btnContinue.setOnClickListener {
            val login = binding.etUserLogin.text.toString()
            val password = binding.etUserPasswords.text.toString()

            if (validateInput(login, password)) {
                showToast(requireContext(), "Successfully")
            }
        }
    }

    private fun validateInput(login: String, password: String): Boolean {
        val loginPattern = Pattern.compile(getString(R.string.a_za_z0_9_6))
        val passwordPattern = Pattern.compile(getString(R.string.a_za_z0_9_6_))

        return when {
            login.isEmpty() -> {
                showToast(requireContext(), "Login must not be empty")
                false
            }

            password.isEmpty() -> {
                showToast(requireContext(), "Password must not be empty")
                false
            }

            !loginPattern.matcher(login).matches() -> {
                showToast(
                    requireContext(),
                    "Логин должен быть больше 6 символов и содержать только английские буквы"
                )
                false
            }

            !passwordPattern.matcher(password).matches() -> {
                showToast(
                    requireContext(),
                    "Пароль должен быть больше 6 символов и содержать только английские буквы и цифры"
                )
                false
            }

            else -> true
        }
    }

}