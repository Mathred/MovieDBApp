package com.example.moviedbapp.model.entities

data class MovieDetailsViewData(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyBriefDTO>?,
    val releaseDate: String,
    val note: String?
)