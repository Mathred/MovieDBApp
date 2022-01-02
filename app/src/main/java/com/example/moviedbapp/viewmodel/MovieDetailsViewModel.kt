package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.entities.MovieListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailsViewModel: ViewModel() {
    val liveData: LiveData<AppState<MovieListItem.MovieData>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<MovieListItem.MovieData>>()

    fun getData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(AppState.Loading)
            delay(1000)
            runCatching {
                _liveData.postValue(AppState.Success(MovieListItem.MovieData(1, "TEST")))
            }.onFailure {
                _liveData.postValue(AppState.Error(it))
            }
        }
    }
}