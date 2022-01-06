package com.example.moviedbapp.ui.rvadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviedbapp.databinding.ItemMovieBinding
import com.example.moviedbapp.databinding.ItemShowMoreCategoryBinding
import com.example.moviedbapp.extensions.toImageUrl
import com.example.moviedbapp.model.entities.MovieCategoryData
import com.example.moviedbapp.model.entities.MovieListItem
import com.example.moviedbapp.utils.Navigator

class MovieListAdapter(
    private val onMovieClick: (Int) -> Unit,
    private val onShowMoreClick: (Navigator.Companion.CategoryType) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieList: List<MovieListItem>? = null

    inner class MovieVH(binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvTitle
        val image = binding.ivMovie
        val card = binding.cvMovie
        val adult = binding.tvAdult
    }

    inner class ShowMoreVH(binding: ItemShowMoreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val card = binding.cvMore
    }

    override fun getItemViewType(position: Int) =
        if (position < itemCount - 1) {
            VIEW_TYPE_MOVIE
        } else {
            VIEW_TYPE_SHOW_MORE
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_MOVIE) {
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            MovieVH(binding)
        } else {
            val binding = ItemShowMoreCategoryBinding.inflate(layoutInflater, parent, false)
            ShowMoreVH(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_MOVIE) {
            if (holder is MovieVH) {
                if (movieList?.getOrNull(position) is MovieListItem.MovieData) {
                    holder.name.text =
                        (movieList?.getOrNull(position) as MovieListItem.MovieData).title
                    (movieList?.getOrNull(position) as MovieListItem.MovieData).posterUrl?.let {
                        holder.image.load(it.toImageUrl())
                    }
                    holder.adult.isVisible =
                        (movieList?.getOrNull(position) as MovieListItem.MovieData).adult
                    holder.card.setOnClickListener {
                        onMovieClick.invoke((movieList?.getOrNull(position) as MovieListItem.MovieData).id)
                    }
                }
            }
        } else {
            if (holder is ShowMoreVH) {
                if (movieList?.getOrNull(position) is MovieListItem.ShowMoreButton)
                holder.card.setOnClickListener {
                    onShowMoreClick.invoke((movieList?.getOrNull(position) as MovieListItem.ShowMoreButton).category)
                }
            }
        }
    }

    override fun getItemCount() = movieList?.size ?: 0

    fun setData(movieCategoryData: MovieCategoryData) {
        movieList = movieCategoryData.movieList
        notifyItemRangeInserted(0, movieList?.size ?: 1 - 1)
    }

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_SHOW_MORE = 1
    }
}