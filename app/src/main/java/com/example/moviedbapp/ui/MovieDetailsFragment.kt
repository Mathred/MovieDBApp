package com.example.moviedbapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.FragmentMovieDetailsBinding
import com.example.moviedbapp.extensions.showErrorLoadingDialog
import com.example.moviedbapp.model.entities.MovieListItem
import com.example.moviedbapp.viewmodel.AppState
import com.example.moviedbapp.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var viewModel: MovieDetailsViewModel
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = arguments?.getInt(ID) ?: -1
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            initVm()
            initObservers()
            requestData(id)
        }
        return binding.root
    }

    private fun FragmentMovieDetailsBinding.initVm() {
        viewModel = ViewModelProvider(this@MovieDetailsFragment)[MovieDetailsViewModel::class.java]
    }

    private fun FragmentMovieDetailsBinding.initViews(data: MovieListItem.MovieData) {
        tvName.text = data.name
    }

    private fun FragmentMovieDetailsBinding.initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it) {
                is AppState.Loading -> {
                    progress.isVisible = true
                }
                is AppState.Error -> {
                    progress.isVisible = false
                    showErrorLoadingDialog(requireContext())
                }
                is AppState.Success -> {
                    progress.isVisible = false
                    initViews(it.data)
                }
            }
        }
        )
    }

    private fun FragmentMovieDetailsBinding.requestData(id: Int) {
        viewModel.getData(id)
    }

    companion object {

        const val ID = "ID"

        fun newInstance(bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}