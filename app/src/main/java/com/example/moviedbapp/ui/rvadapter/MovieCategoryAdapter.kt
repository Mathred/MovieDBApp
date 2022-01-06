package com.example.moviedbapp.ui.rvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.databinding.ItemMovieCategoryBinding
import com.example.moviedbapp.model.entities.MovieCategoryData
import com.example.moviedbapp.utils.Navigator

class MovieCategoryAdapter(
    private val context: Context,
    private var onMovieClick: (Int) -> Unit,
    private var onShowMoreClick: (Navigator.Companion.CategoryType) -> Unit
) : RecyclerView.Adapter<MovieCategoryAdapter.ViewHolder>() {

    private var movieCategoriesData: List<MovieCategoryData>? = null

    inner class ViewHolder(binding: ItemMovieCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val category = binding.tvCategory
        val rvMovieList = binding.rvMovies
    }

    fun setData(data: List<MovieCategoryData>) {
        notifyItemRangeRemoved(0,movieCategoriesData?.size?:1-1) //Crash fix https://stackoverflow.com/a/37050829
        movieCategoriesData = data
        notifyItemRangeInserted(0, movieCategoriesData?.size?:1-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieCategoryBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            category.text = movieCategoriesData?.getOrNull(position)?.category
            rvMovieList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter =
                MovieListAdapter(onMovieClick, onShowMoreClick)
            rvMovieList.adapter = adapter
            movieCategoriesData?.getOrNull(position)?.let {
                adapter.setData(it)
            }

        }
    }

    override fun getItemCount() = movieCategoriesData?.size ?: 0

}