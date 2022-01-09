package com.example.moviedbapp.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsDTO(
    val id: Int,
    val title: String,
    val original_title: String,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyBriefDTO>,
    val production_countries: List<ProductionCountryDTO>,
    val release_date: String,
) : Parcelable

@Parcelize
data class ProductionCompanyBriefDTO(
    val name: String,
    val id: Int,
    val logo_path: String?
) : Parcelable

@Parcelize
data class ProductionCountryDTO(
    val iso_3166_1: String,
    val name: String
): Parcelable