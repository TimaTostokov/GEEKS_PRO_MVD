package com.mvdasker.geeks_pro_mvd.utils.ext

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
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
import kotlin.math.roundToInt

object Extensions {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.noInternetSnackbar() {
        view?.apply {
            val snackbar = Snackbar.make(this, R.string.no_internet_text, Snackbar.LENGTH_SHORT)
            val view = snackbar.view
            val mTextView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            } else {
                mTextView.gravity = Gravity.CENTER_HORIZONTAL
            }
                .apply {
                    view.background =
                        ContextCompat.getDrawable(context, R.drawable.snackbar_background)

                    val params = view.layoutParams
                    if (params is FrameLayout.LayoutParams) {
                        params.gravity = Gravity.TOP
                        params.topMargin = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            56f,
                            context.resources.displayMetrics
                        ).roundToInt()
                    } else if (params is CoordinatorLayout.LayoutParams) {
                        params.gravity = Gravity.TOP
                        params.topMargin = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            56f,
                            context.resources.displayMetrics
                        ).roundToInt()
                    }
                    view.layoutParams = params
                }
            snackbar.show()
        }
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
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