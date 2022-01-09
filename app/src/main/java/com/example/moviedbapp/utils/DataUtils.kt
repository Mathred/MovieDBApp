package com.example.moviedbapp.utils

import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.model.room.entities.MovieHistoryEntity
import com.example.moviedbapp.model.room.entities.MovieNoteEntity

fun MovieHistoryEntity.toMovieDetailsDto() =
    MovieDetailsDTO(id, title, original_title, poster_path, listOf(), listOf(), "")

fun MovieDetailsDTO.toMovieHistoryEntity() =
    MovieHistoryEntity(id, title, original_title, poster_path, release_date)

fun MovieDetailsDTO.toMovieNoteEntity() =
    MovieNoteEntity(id, note = null)
