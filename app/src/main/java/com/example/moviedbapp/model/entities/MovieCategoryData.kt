package com.example.moviedbapp.model.entities

data class MovieCategoryData(
    val category: String? = null,
    val movieList: List<MovieListItem.MovieData>? = null
) {
    fun getDefaultMovieCategoryList(): List<MovieCategoryData> {
        return listOf(
            MovieCategoryData(
                "1",
                listOf(
                    MovieListItem.MovieData(
                        1,
                        "1"
                    ),
                    MovieListItem.MovieData(
                        2,
                        "2"
                    ),
                )
            ),
            MovieCategoryData(
                "2",
                listOf(
                    MovieListItem.MovieData(
                        1,
                        "1"
                    ),
                    MovieListItem.MovieData(
                        2,
                        "2"
                    ),
                )
            )
        )
    }
}
