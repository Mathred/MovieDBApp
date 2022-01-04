package com.example.moviedbapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.moviedbapp.MovieDbApp
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.FragmentMovieDetailsBinding
import com.example.moviedbapp.extensions.showErrorLoadingDialog
import com.example.moviedbapp.extensions.toImageUrl
import com.example.moviedbapp.extensions.toSvgUrl
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
        val id = arguments?.getInt(MOVIE_ID) ?: -1
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            initVm()
            initObservers()
            requestData(id)
        }
        return binding.root
    }

    private fun initVm() {
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
    }

    private fun FragmentMovieDetailsBinding.initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is AppState.Loading -> {
                    progress.isVisible = true
                }
                is AppState.Error -> {
                    progress.isVisible = false
                    showErrorLoadingDialog(requireContext(), it.error)
                }
                is AppState.Success -> {
                    progress.isVisible = false
                    tvTitle.text = it.data.title
                    ivMovie.load(it.data.poster_path?.toImageUrl())
                    it.data.production_companies.getOrNull(0)?.logo_path?.let { logoPath ->
                        Log.d("SAY", "logo_path = $logoPath, url = ${logoPath.toImageUrl()}, svgUrl = ${logoPath.toSvgUrl()}")
                        ivCompanyLogo.load(logoPath.toSvgUrl())
                    }
                }
            }
        }
    }

    private fun requestData(id: Int) {
        viewModel.getData((activity?.application as? MovieDbApp)?.movieDbApi,id)
    }

    companion object {

        const val MOVIE_ID = "MOVIE_ID"
        fun newInstance(bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}