package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.entities.MovieBriefDTO
import com.example.moviedbapp.model.entities.MovieCategoryData
import com.example.moviedbapp.model.entities.MovieListItem
import com.example.moviedbapp.model.repo.MovieDbApi
import com.example.moviedbapp.utils.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val liveData: LiveData<LoadState<List<MovieCategoryData>>> get() = _liveData
    private var _liveData = MutableLiveData<LoadState<List<MovieCategoryData>>>()

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
                _liveData.postValue(LoadState.Loading)
                runCatching {
                    val topRatedMoviesList = movieDbApi.getTopRatedList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    val mlTopRated: MutableList<MovieListItem> = mutableListOf()
                    mlTopRated.addAll(topRatedMoviesList)
                    mlTopRated.add(MovieListItem.ShowMoreButton(Navigator.Companion.CategoryType.TOP_RATED))
                    val topRatedMoviesCategory = MovieCategoryData(
                        Navigator.Companion.CategoryType.TOP_RATED.displayedName,
                        mlTopRated.toList()
                    )
                    val popularMovieList = movieDbApi.getPopularMovieList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    val mlTopPopular: MutableList<MovieListItem> = mutableListOf()
                    mlTopPopular.addAll(popularMovieList)
                    mlTopPopular.add(MovieListItem.ShowMoreButton(Navigator.Companion.CategoryType.POPULAR))
                    val popularCategory = MovieCategoryData(
                        Navigator.Companion.CategoryType.POPULAR.displayedName,
                        mlTopPopular
                    )
                    val upcomingMovieList = movieDbApi.getUpcomingMovieList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    val mlTopUpcoming: MutableList<MovieListItem> = mutableListOf()
                    mlTopUpcoming.addAll(upcomingMovieList)
                    mlTopUpcoming.add(MovieListItem.ShowMoreButton(Navigator.Companion.CategoryType.UPCOMING))
                    val upcomingCategory = MovieCategoryData(
                        Navigator.Companion.CategoryType.UPCOMING.displayedName,
                        mlTopUpcoming
                    )
                    _liveData.postValue(
                        LoadState.Success(
                            listOf(
                                topRatedMoviesCategory,
                                popularCategory,
                                upcomingCategory
                            )
                        )
                    )
                }.onFailure {
                    _liveData.postValue(LoadState.Error(it))
                }
            }
        }
    }
}