package com.example.moviedbapp.model.repo

import com.example.moviedbapp.model.entities.ActorData
import com.example.moviedbapp.model.entities.MovieData

interface MovieDbApi {
    fun getMovie(): MovieData
    fun getActor(): ActorData
}