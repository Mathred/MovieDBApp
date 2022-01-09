package com.example.moviedbapp.extensions

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.moviedbapp.ui.MovieDetailsFragment
import com.example.moviedbapp.R


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
        ?.add(R.id.container, MovieDetailsFragment.newInstance(bundle))
        ?.addToBackStack("")
        ?.commit()
}