package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.MovieDbApp.Companion.getMovieDetailsDao
import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.model.repo.LocalRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieHistoryViewModel(
    private val localRepo: LocalRepoImpl = LocalRepoImpl(getMovieDetailsDao())
) : ViewModel() {

    val liveData: LiveData<AppState<List<MovieDetailsDTO>>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<List<MovieDetailsDTO>>>()

    fun requestData() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(AppState.Success(localRepo.getAllMovieDetailsHistory()))
        }
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            localRepo.clearMovieHistory()
        }
    }
}