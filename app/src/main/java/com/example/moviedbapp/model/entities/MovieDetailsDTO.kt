package com.example.moviedbapp.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsDTO(
    val id: Int,
    val title: String,
    val original_title: String,
    val release_date: String,
) : Parcelable
