package com.mvdasker.geeks_pro_mvd.utils.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.mvdasker.geeks_pro_mvd.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Extensions {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.snackbar(msg: String) {
        view?.apply {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    fun Context.showNoInternetSnackbar() {
        val snackbarLayout =
            LayoutInflater.from(this).inflate(R.layout.custom_snackbar_layout, null) as LinearLayout
        val textView = snackbarLayout.findViewById<TextView>(R.id.snackbar_text)
        textView.text =
            "Сеть недоступна. Убедитесь, что Wi-Fi включен или мобильные данные активны."

        val rootView = (this as Activity).findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view as ViewGroup
        snackbarView.removeAllViews()
        snackbarView.addView(snackbarLayout, 0)

        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.setMargins(0, 0, 0, 0)
        params.gravity = Gravity.TOP
        snackbarView.layoutParams = params

        snackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "",
        negativeButtonText: String = "",
        onPositiveButtonClick: (() -> Unit)? = null,
        onNegativeButtonClick: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                onPositiveButtonClick?.invoke()
                dialog.dismiss()
            }

        builder.setNegativeButton(negativeButtonText) { dialog, _ ->
            onNegativeButtonClick?.invoke()
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun Fragment.disableScreenShot(isSecure: Boolean) {
        if (isSecure) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}

inline fun <T> Fragment.observeData(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(state) {
        flow.collect { data ->
            block(data)
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val targetFormat = SimpleDateFormat("dd MMMM yyyy")
    val data: Date = originalFormat.parse(date)!!
    val formattedDate = targetFormat.format(data)
    return formattedDate
}