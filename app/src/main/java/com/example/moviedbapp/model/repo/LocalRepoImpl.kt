package com.example.moviedbapp.model.repo

import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.model.room.dao.MovieDetailsDao
import com.example.moviedbapp.utils.toMovieDetailsDto
import com.example.moviedbapp.utils.toMovieHistoryEntity
import com.example.moviedbapp.utils.toMovieNoteEntity

class LocalRepoImpl(private val localDataSource: MovieDetailsDao) : LocalRepo {

    override suspend fun getAllMovieDetailsHistory(): List<MovieDetailsDTO> {
        return localDataSource.getAllMovieDetails().map { it.toMovieDetailsDto() }
    }

    override suspend fun saveMovieHistoryEntity(movieDetailsDTO: MovieDetailsDTO) {
        localDataSource.insertMovieHistory(movieDetailsDTO.toMovieHistoryEntity())
    }

    override suspend fun clearMovieHistory() {
        localDataSource.clearAll()
    }

    override suspend fun updateNoteByMovieId(id: Int, note: String) {
        localDataSource.updateNoteByMovieId(id, note)
    }

    override suspend fun getNoteByMovieId(id: Int): String? {
        return localDataSource.getNoteByMovieId(id).note
    }

    override suspend fun saveMovieNoteEntity(movieDetailsDTO: MovieDetailsDTO) {
        localDataSource.insertMovieNote(movieDetailsDTO.toMovieNoteEntity())
    }

    override suspend fun deleteNoteByMovieId(id: Int) {
        localDataSource.deleteNoteByMovieId(localDataSource.getNoteByMovieId(id))
    }

}