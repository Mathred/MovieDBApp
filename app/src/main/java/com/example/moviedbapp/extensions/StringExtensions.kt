package com.example.moviedbapp.extensions

import com.example.moviedbapp.MovieDbApp.Companion.IMAGE_SECURE_URL
import com.example.moviedbapp.MovieDbApp.Companion.POSTER_SIZE_URL
import com.example.moviedbapp.MovieDbApp.Companion.URL_ORIGINAL_IMAGE_SIZE

fun String.toImageUrl() =
    "$IMAGE_SECURE_URL${POSTER_SIZE_URL}$this"

fun String.toSvgUrl() =
    "$IMAGE_SECURE_URL${URL_ORIGINAL_IMAGE_SIZE}$this".replace(".png",".svg")