package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.auth

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import com.mvdasker.geeks_pro_mvd.R

class MailInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : CustomInputLayout(context, attrs, defStyleAttr) {
    override val errorMessageId = R.string.login_error

    override fun innerIsValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text()).matches()
    }
}