package com.example.moviedbapp.model.repo

import com.example.moviedbapp.model.entities.ActorData
import com.example.moviedbapp.model.entities.MovieListItem

interface MovieDbApi {
    fun getMovieList(): List<MovieListItem.MovieData>
    fun getMovie(): MovieListItem.MovieData
    fun getActor(): ActorData
}