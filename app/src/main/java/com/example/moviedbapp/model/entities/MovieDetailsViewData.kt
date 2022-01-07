package com.example.moviedbapp.model.entities

import java.util.*

data class MovieDetailsViewData(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyBriefDTO>,
    val productionCountries: List<ProductionCountryDTO>,
    val releaseDate: Date?,
    val note: String?
)