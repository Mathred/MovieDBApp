package com.example.moviedbapp.model.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedbapp.model.entities.MovieBriefDTO
import com.example.moviedbapp.utils.Navigator
import retrofit2.HttpException
import java.io.IOException

class MovieDbPagingSource(private val movieDbApi: MovieDbApi, private val categoryType: Navigator.Companion.CategoryType) : PagingSource<Int, MovieBriefDTO>() {

    override fun getRefreshKey(state: PagingState<Int, MovieBriefDTO>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieBriefDTO> {
        val page = params.key ?: 1
        return try {
            val response = when(categoryType) {
                Navigator.Companion.CategoryType.POPULAR -> {
                    movieDbApi.getPopularMovieList(page = page)
                }
                Navigator.Companion.CategoryType.UPCOMING -> {
                    movieDbApi.getUpcomingMovieList(page = page)
                }
                Navigator.Companion.CategoryType.TOP_RATED -> {
                    movieDbApi.getTopRatedList(page = page)
                }
            }
            LoadResult.Page(
                response.results,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}