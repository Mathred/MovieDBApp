package com.example.moviedbapp.model.entities

data class MovieBriefDTO(
    val id:Int,
    val title: String,
    val originalTitle: String?,
    val poster_path: String?
) {
    fun toMovieListViewData() = MovieListItem.MovieData(
        id = id,
        title = title,
        posterUrl = poster_path
    )
}
