package com.example.moviedbapp.model.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieHistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val original_title: String,
    val poster_path: String?,
    val release_date: String,
)
