package com.example.moviedbapp.extensions

import android.content.Context
import android.content.DialogInterface
import com.example.moviedbapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showAlertDialog(
    context: Context,
    title: String,
    message: String,
    neutralButtonText: String? = null,
    neutralButtonListener: DialogInterface.OnClickListener? = null,
    negativeButtonText: String? = null,
    negativeButtonListener: DialogInterface.OnClickListener? = null,
    positiveButtonText: String? = null,
    positiveButtonListener: DialogInterface.OnClickListener? = null
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(neutralButtonText, neutralButtonListener)
        .setNegativeButton(negativeButtonText, negativeButtonListener)
        .setPositiveButton(positiveButtonText, positiveButtonListener)
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
        positiveButtonText = context.getString(R.string.error_loading_data_positive),
        positiveButtonListener = { dialog, _ -> dialog.dismiss() }
    )
}
