package com.example.moviedbapp.extensions

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

@SuppressLint("SimpleDateFormat")
private val dateFormatYear = SimpleDateFormat("yyyy")

fun String.toDate(): Date? {
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.getDateYear(): String {
    return dateFormatYear.format(this)
}