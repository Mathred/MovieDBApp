package com.example.moviedbapp.ui.rvadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviedbapp.databinding.ItemCategoryFullListBinding
import com.example.moviedbapp.extensions.toImageUrl
import com.example.moviedbapp.model.entities.MovieBriefDTO

class CategoryFullListAdapter(private val onMovieClick: (Int) -> Unit) : PagingDataAdapter<MovieBriefDTO, CategoryFullListAdapter.ViewHolder>(MovieBriefCallBack()) {

    private class MovieBriefCallBack : DiffUtil.ItemCallback<MovieBriefDTO>() {
        override fun areItemsTheSame(oldItem: MovieBriefDTO, newItem: MovieBriefDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieBriefDTO, newItem: MovieBriefDTO): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFullListAdapter.ViewHolder {
        return ViewHolder(ItemCategoryFullListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ViewHolder(binding: ItemCategoryFullListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val container = binding.clContainer
        private val poster = binding.ivPoster
        private val title = binding.tvTitle

        fun bind(item: MovieBriefDTO?) {
            title.text = item?.title
            poster.load(item?.poster_path?.toImageUrl())
            container.setOnClickListener {
                item?.id?.let { id -> onMovieClick.invoke(id) }
            }
        }

    }



}