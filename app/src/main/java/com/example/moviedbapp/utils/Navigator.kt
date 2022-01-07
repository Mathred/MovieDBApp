package com.example.moviedbapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviedbapp.ContactsFragment
import com.example.moviedbapp.LocationFragment
import com.example.moviedbapp.R
import com.example.moviedbapp.ui.*

class Navigator {
    companion object {
        enum class CategoryType(val displayedName: String) {
            TOP_RATED("Top rated"),
            POPULAR("Popular"),
            UPCOMING("Upcoming")
        }
    }
}

fun Fragment.openMovie(
    id: Int
) {
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
        ?.add(R.id.container, MovieDetailsFragment.newInstance(id))
        ?.addToBackStack("")
        ?.commit()
}

fun Fragment.openCategoryFullList(categoryType: Navigator.Companion.CategoryType) {
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
        ?.add(R.id.container, CategoryFullListFragment.newInstance(categoryType.ordinal))
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

fun AppCompatActivity.openContacts() {
    this.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        )
        .replace(R.id.container, ContactsFragment.newInstance())
        .addToBackStack("")
        .commit()
}

fun Fragment.openLocation(location: String) {
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
        ?.add(R.id.container, LocationFragment.newInstance(location))
        ?.addToBackStack("")
        ?.commit()
}