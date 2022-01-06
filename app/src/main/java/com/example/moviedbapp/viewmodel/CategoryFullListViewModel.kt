package com.example.moviedbapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.moviedbapp.model.entities.MovieBriefDTO
import com.example.moviedbapp.model.repo.MovieDbApi
import com.example.moviedbapp.model.repo.MovieDbRepository
import com.example.moviedbapp.utils.Navigator
import kotlinx.coroutines.flow.Flow

class CategoryFullListViewModel : ViewModel() {

    fun fetchMovies(movieDbApi: MovieDbApi, categoryInt: Int): Flow<PagingData<MovieBriefDTO>> {
        val categoryType = Navigator.Companion.CategoryType.values()[categoryInt]
        return MovieDbRepository(movieDbApi).letMovieDataFlow(categoryType = categoryType)
    }
}