package com.example.moviedbapp.viewmodel

sealed class AppState<out T> {
    data class Success<T>(val data: T) : AppState<T>()
    data class Error(val error: Throwable): AppState<Nothing>()
    object Loading: AppState<Nothing>()
}