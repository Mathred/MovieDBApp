package com.example.moviedbapp.viewmodel

sealed class LoadState<out T> {
    data class Success<T>(val data: T) : LoadState<T>()
    data class Error(val error: Throwable): LoadState<Nothing>()
    object Loading: LoadState<Nothing>()
}