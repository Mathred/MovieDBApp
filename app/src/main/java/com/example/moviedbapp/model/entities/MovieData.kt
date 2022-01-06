package com.example.moviedbapp.model.entities

import com.example.moviedbapp.utils.Navigator

sealed class MovieListItem {

    data class ShowMoreButton(
        val category: Navigator.Companion.CategoryType
    ) : MovieListItem()

    data class MovieData(
        val id: Int,
        val adult: Boolean,
        val title: String,
        val posterUrl: String? = null
    ) : MovieListItem()
}
