package com.mvdasker.geeks_pro_mvd.utils.ext

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.mvdasker.geeks_pro_mvd.R

fun Context.showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("ShowToast")
fun Snackbar.showSnack(message: String) {
    view.let {
        Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(context.getString(R.string.ok)) {
                dismiss()
            }
        }
    }
}

fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveButtonText: String = "OK",
    negativeButtonText: String? = null,
    onPositiveButtonClick: (() -> Unit)? = null,
    onNegativeButtonClick: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            onPositiveButtonClick?.invoke()
            dialog.dismiss()
        }

    if (negativeButtonText != null) {
        builder.setNegativeButton(negativeButtonText) { dialog, _ ->
            onNegativeButtonClick?.invoke()
            dialog.dismiss()
        }
    }

    val dialog = builder.create()
    dialog.show()
}

