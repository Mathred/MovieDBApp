package com.example.moviedbapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.moviedbapp.ui.MovieDetailsFragment
import com.example.moviedbapp.R
import com.example.moviedbapp.ui.MainFragment
import com.example.moviedbapp.ui.MovieHistoryFragment
import com.example.moviedbapp.ui.SettingsFragment


fun Fragment.openMovie(
    id: Int
) {
    val bundle = bundleOf(
        MovieDetailsFragment.MOVIE_ID to id
    )
    this
        .activity
        ?.supportFragmentManager
        ?.beginTransaction()
        ?.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right,
        )
        ?.add(R.id.container, MovieDetailsFragment.newInstance(bundle))
        ?.addToBackStack("")
        ?.commit()
}

fun AppCompatActivity.openHome() {
    this.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        )
        .replace(R.id.container, MainFragment.newInstance())
        .addToBackStack("")
        .commit()
}

fun AppCompatActivity.openSettings() {
    this.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        )
        .replace(R.id.container, SettingsFragment.newInstance())
        .addToBackStack("")
        .commit()
}

fun AppCompatActivity.openHistory() {
    this.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        )
        .replace(R.id.container, MovieHistoryFragment.newInstance())
        .addToBackStack("")
        .commit()
}