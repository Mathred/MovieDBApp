package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.entities.MovieCategoryData
import com.example.moviedbapp.model.repo.MovieDbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val liveData: LiveData<AppState<List<MovieCategoryData>>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<List<MovieCategoryData>>>()

    fun getData(movieDbApi: MovieDbApi?) {
        movieDbApi?.let {
            viewModelScope.launch(Dispatchers.IO) {
                _liveData.postValue(AppState.Loading)
                runCatching {
                    val topRatedMoviesList = movieDbApi.getTopRatedList().results.take(10).map {
                        it.toMovieListViewData()
                    }
                    val topRatedMoviesCategory = MovieCategoryData(
                        "Top rated",
                        topRatedMoviesList
                    )
                    val popularMovieList = movieDbApi.getPopularMovieList().results.take(10).map {
                        it.toMovieListViewData()
                    }
                    val popularCategory = MovieCategoryData(
                        "Popular",
                        popularMovieList
                    )
                    val upcomingMovieList = movieDbApi.getUpcomingMovieList().results.take(10).map {
                        it.toMovieListViewData()
                    }
                    val upcomingCategory = MovieCategoryData(
                        "Upcoming",
                        upcomingMovieList
                    )
                    val trendingWeekMovieList = movieDbApi.getTrendingList(timeWindow = "week").results.take(10).map {
                        it.toMovieListViewData()
                    }
                    val trendingWeekCategory = MovieCategoryData(
                        "Trending week",
                        trendingWeekMovieList
                    )
                    _liveData.postValue(
                        AppState.Success(
                            listOf(
                                topRatedMoviesCategory,
                                popularCategory,
                                upcomingCategory,
                                trendingWeekCategory
                            )
                        )
                    )
                }.onFailure {
                    _liveData.postValue(AppState.Error(it))
                }
            }
        }
    }
}