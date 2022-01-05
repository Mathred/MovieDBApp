package com.example.moviedbapp.model.entities

class Paged<T> (
    val page: Int,
    val results: List<T>
)
