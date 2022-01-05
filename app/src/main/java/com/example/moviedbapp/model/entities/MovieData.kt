package com.example.moviedbapp.model.entities

sealed class MovieListItem {

    data class ShowMoreButton(
        val category: String?
    ) : MovieListItem()

    data class MovieData(
        val id: Int,
        val adult: Boolean,
        val title: String,
        val posterUrl: String? = null
    ) : MovieListItem()
}
