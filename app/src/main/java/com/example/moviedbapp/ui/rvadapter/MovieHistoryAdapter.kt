package com.example.moviedbapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviedbapp.databinding.ItemMovieHistoryBinding
import com.example.moviedbapp.extensions.toImageUrl
import com.example.moviedbapp.model.entities.MovieDetailsDTO

class MovieHistoryAdapter: RecyclerView.Adapter<MovieHistoryAdapter.ViewHolder>() {

    private var movieHistoryList: List<MovieDetailsDTO>? = null

    inner class ViewHolder(binding: ItemMovieHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val poster = binding.ivPoster
        val title = binding.tvTitle
    }

    fun setData(data: List<MovieDetailsDTO>) {
        notifyItemRangeRemoved(0,movieHistoryList?.size?:1-1) //Crash fix https://stackoverflow.com/a/37050829
        movieHistoryList = data
        notifyItemRangeInserted(0, data.size - 1)
    }

    fun clearData() {
        val size = movieHistoryList?.size ?: 0
        movieHistoryList = null
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHistoryAdapter.ViewHolder {
        return ViewHolder(ItemMovieHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieHistoryAdapter.ViewHolder, position: Int) {
        val movieDetails = movieHistoryList?.getOrNull(position)
        if (movieDetails != null) {
            holder.poster.load(movieDetails.poster_path?.toImageUrl())
            holder.title.text = movieDetails.title
        }
    }

    override fun getItemCount(): Int = movieHistoryList?.size ?: 0

}