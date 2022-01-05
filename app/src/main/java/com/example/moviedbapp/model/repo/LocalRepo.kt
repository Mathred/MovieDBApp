package com.example.moviedbapp.model.repo

import com.example.moviedbapp.model.entities.MovieDetailsDTO

interface LocalRepo {
    suspend fun getAllMovieDetailsHistory(): List<MovieDetailsDTO>
    suspend fun saveMovieHistoryEntity(movieDetailsDTO: MovieDetailsDTO)
    suspend fun clearMovieHistory()
    suspend fun updateNoteByMovieId(id: Int, note: String)
    suspend fun getNoteByMovieId(id: Int): String?
    suspend fun saveMovieNoteEntity(movieDetailsDTO: MovieDetailsDTO)
    suspend fun deleteNoteByMovieId(id: Int)
}