package com.example.moviedbapp.ui

interface OnMovieClickListener {
    fun onMovieClick(id: Int)
}

interface OnShowMoreClickListener {
    fun onShowMoreClick(category: String)
}