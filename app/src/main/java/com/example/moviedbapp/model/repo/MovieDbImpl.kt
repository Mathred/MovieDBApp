package com.example.moviedbapp.model.repo

import com.example.moviedbapp.model.entities.ActorData
import com.example.moviedbapp.model.entities.MovieListItem

class MovieDbImpl: MovieDbApi {

    //TODO("Replace with API call")
    override fun getMovieList(): List<MovieListItem.MovieData> =
        listOf(
            MovieListItem.MovieData(1, "Movie 1"),
            MovieListItem.MovieData(2,"Movie 2"),
            MovieListItem.MovieData(3,"Movie 3"),
            MovieListItem.MovieData(4,"Movie 4"),
            MovieListItem.MovieData(5,"Movie 5"),
            MovieListItem.MovieData(6,"Movie 6"),
            MovieListItem.MovieData(7,"Movie 7"),
            MovieListItem.MovieData(8,"Movie 8"),
            MovieListItem.MovieData(9,"Movie 9"),
        )

    //TODO("Replace with API call")
    override fun getMovie(): MovieListItem.MovieData =
        MovieListItem.MovieData(1,"Some movie")

    //TODO("Replace with API call")
    override fun getActor(): ActorData =
        ActorData("Some actor")

}