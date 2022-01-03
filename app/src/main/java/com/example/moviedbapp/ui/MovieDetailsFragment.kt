package com.example.moviedbapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.FragmentMovieDetailsBinding
import com.example.moviedbapp.extensions.showAlertDialog
import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.service.MovieDetailsService

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val loadResultReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(MOVIE_DETAILS_LOAD_RESULT)) {
                MOVIE_DETAILS_EMPTY_INTENT -> {
                    showAlertDialog(this@MovieDetailsFragment.requireContext(), "Ошибка загрузки данных", MOVIE_DETAILS_EMPTY_INTENT)
                }
                MOVIE_DETAILS_EMPTY_INPUT_DATA -> {
                    showAlertDialog(this@MovieDetailsFragment.requireContext(), "Ошибка загрузки данных", MOVIE_DETAILS_EMPTY_INPUT_DATA)
                }
                MOVIE_DETAILS_ERROR -> {
                    val errorMessage = intent.getStringExtra(MOVIE_DETAILS_ERROR_MESSAGE) ?: "Неизвестная ошибка"
                    showAlertDialog(this@MovieDetailsFragment.requireContext(), "Ошибка загрузки данных", errorMessage)
                }
                MOVIE_DETAILS_MALFORMED_URL -> {
                    showAlertDialog(this@MovieDetailsFragment.requireContext(), "Ошибка загрузки данных", MOVIE_DETAILS_MALFORMED_URL)
                }
                MOVIE_DETAILS_RESPONSE_SUCCESS -> {
                    val movieDetails = intent.getParcelableExtra<MovieDetailsDTO>(
                        MOVIE_DETAILS_EXTRA)
                    displayDetails(movieDetails)
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = arguments?.getInt(MOVIE_ID) ?: -1
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            registerReceiver(loadResultReceiver)
            requestData(550)
        }
        return binding.root
    }

    private fun registerReceiver(loadResultReceiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(loadResultReceiver, IntentFilter(
            MOVIE_DETAILS_INTENT_FILTER))
    }

    private fun requestData(id: Int) {
        context?.let {
            it.startService(Intent(it, MovieDetailsService::class.java).apply {
                putExtra(MOVIE_ID, id)
            })
        }
    }

    private fun displayDetails(movieDetails: MovieDetailsDTO?) {
        with(binding) {
            tvName.text = movieDetails?.title ?: "-"
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(loadResultReceiver)
        super.onDestroy()
    }

    companion object {

        const val MOVIE_ID = "MOVIE_ID"
        const val MOVIE_DETAILS_INTENT_FILTER = "MOVIE_DETAILS_INTENT_FILTER"
        const val MOVIE_DETAILS_RESPONSE_SUCCESS = "MOVIE_DETAILS_RESPONSE_SUCCESS"
        const val MOVIE_DETAILS_EXTRA = "MOVIE_DETAILS_TITLE"
        const val MOVIE_DETAILS_EMPTY_INTENT = "MOVIE_DETAILS_EMPTY_INTENT"
        const val MOVIE_DETAILS_EMPTY_INPUT_DATA = "MOVIE_DETAILS_EMPTY_INPUT_DATA"
        const val MOVIE_DETAILS_ERROR = "MOVIE_DETAILS_ERROR"
        const val MOVIE_DETAILS_ERROR_MESSAGE = "MOVIE_DETAILS_ERROR_MESSAGE"
        const val MOVIE_DETAILS_LOAD_RESULT = "MOVIE_DETAILS_LOAD_RESULT"
        const val MOVIE_DETAILS_MALFORMED_URL = "MOVIE_DETAILS_MALFORMED_URL"


        fun newInstance(bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}