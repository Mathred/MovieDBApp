package com.example.moviedbapp.utils

import android.content.Context
import com.example.moviedbapp.R

class Preferences(
    private val context: Context
) {

    fun getAdultContentEnabledOption(): Boolean {
        val sharedPref = context.getSharedPreferences(ADULT_CONTENT_ENABLED, Context.MODE_PRIVATE)
        val defaultValue = context.resources.getBoolean(R.bool.adult_content_enabled)
        return sharedPref.getBoolean(ADULT_CONTENT_ENABLED, defaultValue)
    }

    fun setAdultContentEnabledOption(value: Boolean) {
        context
            .getSharedPreferences(ADULT_CONTENT_ENABLED, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(ADULT_CONTENT_ENABLED, value)
            .apply()
    }

    fun getLongTitlesEnabledOption(): Boolean {
        val sharedPref = context.getSharedPreferences(LONG_TITLES_ENABLED, Context.MODE_PRIVATE)
        val defaultValue = context.resources.getBoolean(R.bool.long_titles_enabled)
        return sharedPref.getBoolean(LONG_TITLES_ENABLED, defaultValue)
    }

    fun setLongTitlesEnabledOption(value: Boolean) {
        context
            .getSharedPreferences(LONG_TITLES_ENABLED, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(LONG_TITLES_ENABLED, value)
            .apply()

    }

    companion object {
        const val ADULT_CONTENT_ENABLED = "ADULT_CONTENT_ENABLED"
        const val LONG_TITLES_ENABLED = "LONG_TITLES_ENABLED"
    }
}
