package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.entities.MovieBriefDTO
import com.example.moviedbapp.model.entities.MovieCategoryData
import com.example.moviedbapp.model.repo.MovieDbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val liveData: LiveData<AppState<List<MovieCategoryData>>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<List<MovieCategoryData>>>()

    fun getData(movieDbApi: MovieDbApi?, showAdultContent: Boolean, showLongTitles: Boolean) {
        movieDbApi?.let {
            val filterAdultContent: (MovieBriefDTO) -> Boolean = if (showAdultContent) {
                { true }
            } else {
                { !it.adult }
            }

            val filterLongTitles: (MovieBriefDTO) -> Boolean = if (showLongTitles) {
                { true }
            } else {
                { it.title.length <= 10 }
            }

            viewModelScope.launch(Dispatchers.IO) {
                _liveData.postValue(AppState.Loading)
                runCatching {
                    val topRatedMoviesList = movieDbApi.getTopRatedList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    val topRatedMoviesCategory = MovieCategoryData(
                        "Top rated",
                        topRatedMoviesList
                    )
                    val popularMovieList = movieDbApi.getPopularMovieList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    val popularCategory = MovieCategoryData(
                        "Popular",
                        popularMovieList
                    )
                    val upcomingMovieList = movieDbApi.getUpcomingMovieList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    val upcomingCategory = MovieCategoryData(
                        "Upcoming",
                        upcomingMovieList
                    )
                    val trendingWeekMovieList = movieDbApi.getTrendingList(timeWindow = "week").results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
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