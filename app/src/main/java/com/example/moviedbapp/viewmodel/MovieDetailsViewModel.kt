package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.model.repo.MovieDbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {
    val liveData: LiveData<AppState<MovieDetailsDTO>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<MovieDetailsDTO>>()

    fun getData(movieDbApi: MovieDbApi?, id: Int) {
        movieDbApi?.let {
            viewModelScope.launch(Dispatchers.IO) {
                _liveData.postValue(AppState.Loading)
                runCatching {
                    movieDbApi.getMovieDetails(id)
                    _liveData.postValue(AppState.Success(movieDbApi.getMovieDetails(id)))
                }.onFailure {
                    _liveData.postValue(AppState.Error(it))
                }
            }
        }
    }
}