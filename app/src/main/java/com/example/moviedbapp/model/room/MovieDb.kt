package com.example.moviedbapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviedbapp.model.room.dao.MovieDetailsDao
import com.example.moviedbapp.model.room.entities.MovieHistoryEntity
import com.example.moviedbapp.model.room.entities.MovieNoteEntity

@Database(entities = [MovieHistoryEntity::class, MovieNoteEntity::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDetailsDao(): MovieDetailsDao
}