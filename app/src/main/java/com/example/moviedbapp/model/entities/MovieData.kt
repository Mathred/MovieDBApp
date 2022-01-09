package com.example.moviedbapp.model.entities

import android.graphics.drawable.Drawable

sealed class MovieListItem {

    data class ShowMoreButton(
        val category: String?
    ) : MovieListItem()

    data class MovieData(
        val id: Int,
        val name: String,
        val image: Drawable? = null
    ) : MovieListItem()
}
