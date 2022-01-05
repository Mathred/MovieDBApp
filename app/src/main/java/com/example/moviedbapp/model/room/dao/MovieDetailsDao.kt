package com.example.moviedbapp.model.room.dao

import androidx.room.*
import com.example.moviedbapp.model.room.entities.MovieHistoryEntity
import com.example.moviedbapp.model.room.entities.MovieNoteEntity

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM MovieHistoryEntity")
    fun getAllMovieDetails(): List<MovieHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieHistory(entity: MovieHistoryEntity)

    @Query("DELETE FROM MovieHistoryEntity")
    fun clearAll()

    @Query("UPDATE MovieNoteEntity SET note =:note WHERE id = :id")
    fun updateNoteByMovieId(id: Int, note: String)

    @Query("SELECT * FROM MovieNoteEntity WHERE id = :id")
    fun getNoteByMovieId(id: Int): MovieNoteEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieNote(entity: MovieNoteEntity)

    @Delete
    fun deleteNoteByMovieId(movieNoteEntity: MovieNoteEntity)

}