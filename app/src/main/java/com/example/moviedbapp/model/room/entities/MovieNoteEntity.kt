package com.example.moviedbapp.model.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieNoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val note: String?
)
