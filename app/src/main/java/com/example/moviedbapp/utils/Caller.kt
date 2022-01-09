package com.example.moviedbapp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.moviedbapp.ContactsFragment.Companion.REQUEST_CODE_CALL
import com.example.moviedbapp.R

fun Context.dialPhone(phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    } catch (e: Throwable) {
        showTextToast(R.string.toast_phone_failed)
        e.localizedMessage?.let { Log.e("Caller", it) }
    }
}

fun Fragment.callPhone(phone: String) {
    try {
        if (this.requireContext().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL)
        } else {
            this.requireContext().startCallIntent(phone)
        }
    } catch (e: Throwable) {
        this.context?.showTextToast(R.string.toast_phone_failed)
        e.localizedMessage?.let { Log.e("Caller", it) }
    }
}

private fun Context.startCallIntent(phone: String) {
    val intent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null))
    startActivity(intent)
}