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
                    val ml: MutableList<MovieListItem> = mutableListOf()
                    ml.addAll(topRatedMoviesList)
                    ml.add(MovieListItem.ShowMoreButton(Navigator.Companion.CategoryType.TOP_RATED))
                    val topRatedMoviesCategory = MovieCategoryData(
                        Navigator.Companion.CategoryType.TOP_RATED.displayedName,
                        ml.toList()
                    )
                    val popularMovieList = movieDbApi.getPopularMovieList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    ml.clear()
                    ml.addAll(popularMovieList)
                    ml.add(MovieListItem.ShowMoreButton(Navigator.Companion.CategoryType.POPULAR))
                    val popularCategory = MovieCategoryData(
                        Navigator.Companion.CategoryType.POPULAR.displayedName,
                        ml
                    )
                    val upcomingMovieList = movieDbApi.getUpcomingMovieList().results.filter(filterAdultContent).filter(filterLongTitles).take(10).map {
                        it.toMovieListViewData()
                    }
                    ml.clear()
                    ml.addAll(upcomingMovieList)
                    ml.add(MovieListItem.ShowMoreButton(Navigator.Companion.CategoryType.UPCOMING))
                    val upcomingCategory = MovieCategoryData(
                        Navigator.Companion.CategoryType.UPCOMING.displayedName,
                        ml
                    )
                    _liveData.postValue(
                        AppState.Success(
                            listOf(
                                topRatedMoviesCategory,
                                popularCategory,
                                upcomingCategory
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