package com.example.moviedbapp.model.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviedbapp.model.entities.MovieBriefDTO
import com.example.moviedbapp.utils.Navigator
import kotlinx.coroutines.flow.Flow

class MovieDbRepository(
    private val movieDbApi: MovieDbApi
) {

    fun letMovieDataFlow(pagingConfig: PagingConfig = getDefaultPageConfig(), categoryType: Navigator.Companion.CategoryType): Flow<PagingData<MovieBriefDTO>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MovieDbPagingSource(movieDbApi, categoryType) }
        ).flow
    }

    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }

}