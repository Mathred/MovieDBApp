package com.example.moviedbapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.model.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val liveData: LiveData<AppState<Any>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<Any>>()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(AppState.Loading)
            delay(1000)
            kotlin.runCatching {
                _liveData.postValue(AppState.Success(Any()))
            }.onFailure {
                _liveData.postValue(AppState.Error(it))
            }
        }
    }
}