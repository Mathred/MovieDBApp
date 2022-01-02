package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.entities.MovieCategoryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val liveData: LiveData<AppState<List<MovieCategoryData>>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<List<MovieCategoryData>>>()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(AppState.Loading)
            delay(1000)
            runCatching {
                _liveData.postValue(AppState.Success(MovieCategoryData().getDefaultMovieCategoryList()))
            }.onFailure {
                _liveData.postValue(AppState.Error(it))
            }
        }
    }
}