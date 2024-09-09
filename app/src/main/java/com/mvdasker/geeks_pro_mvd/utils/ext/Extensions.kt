package com.mvdasker.geeks_pro_mvd.utils.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.PlayerItem
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

    @SuppressLint("ObsoleteSdkInt")
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

    fun ImageView.loadImage(url: String) {
        Glide.with(this).load(url).into(this)
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    inline fun <T> Fragment.observeData(
        flow: Flow<T>,
        lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline block: (T) -> Unit,
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

    fun <T, R> mapToMediaItems(
        items1: List<T>,
        items2: List<R>,
        mapper1: (T) -> PlayerItem?,
        mapper2: (R) -> PlayerItem?
    ): List<PlayerItem> {
        val list1 = items1.mapNotNull { mapper1(it) }
        val list2 = items2.mapNotNull { mapper2(it) }
        return list1 + list2
    }

    fun <T> convertToUrlArray(
        inputList: List<T>?,
        extractUrl: (T) -> String?
    ): List<String> {
        val urlArray = mutableListOf<String>()

        inputList?.forEach { item ->
            val urlString = extractUrl(item)
            Log.d("convertToUrlArray", "Processing item: $urlString")

            if (urlString != null && Patterns.WEB_URL.matcher(urlString).matches()) {
                urlArray.add(urlString)
            }
        }

        Log.d("convertToUrlArray", "Final urlArray: $urlArray")
        return urlArray
    }

    fun CardView.highlightItemCard() {
        val resources: Resources = context.resources
        val backgroundColor = ContextCompat.getColor(
            context,
            R.color.background_item
        )
        val whiteColor = ContextCompat.getColor(
            context,
            R.color.white
        )
        setCardBackgroundColor(backgroundColor)
        postDelayed({
            setCardBackgroundColor(whiteColor)
        }, 1000)
    }

    fun View.highlightItem() {
        val resources: Resources = context.resources
        val backgroundColor = ContextCompat.getColor(
            context,
            R.color.background_item
        )
        val whiteColor = ContextCompat.getColor(
            context,
            R.color.white
        )
        setBackgroundColor(backgroundColor)
        postDelayed({
            setBackgroundColor(whiteColor)
        }, 1000)
    }

}
