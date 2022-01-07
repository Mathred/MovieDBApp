package com.example.moviedbapp.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.moviedbapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showAlertDialog(
    context: Context,
    title: String,
    message: String,
    neutralButtonText: String? = null,
    neutralButtonListener: DialogInterface.OnClickListener? = null,
    negativeButtonText: String? = null,
    negativeButtonAction: (() -> Unit)? = null,
    positiveButtonText: String? = null,
    positiveButtonAction: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(neutralButtonText, neutralButtonListener)
        .setNegativeButton(negativeButtonText) { dialog, _ ->
            negativeButtonAction?.invoke()
            dialog.dismiss()
        }
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            positiveButtonAction?.invoke()
            dialog.dismiss()
        }
        .show()
}

fun showErrorLoadingDialog(
    context: Context,
    error: Throwable? = null
) {
    showAlertDialog(
        context,
        title = context.getString(R.string.error_loading_data_title),
        message = error?.localizedMessage ?: context.getString(R.string.error_loading_data_message),
        positiveButtonText = context.getString(R.string.error_loading_data_positive)
    )
}

fun Context.showTextToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showTextToast(@StringRes textId: Int) {
    Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()
}
