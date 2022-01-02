package com.example.moviedbapp.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviedbapp.BuildConfig
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.FragmentMovieDetailsBinding
import com.example.moviedbapp.extensions.showErrorLoadingDialog
import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.model.entities.MovieListItem
import com.example.moviedbapp.viewmodel.AppState
import com.example.moviedbapp.viewmodel.MovieDetailsViewModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var viewModel: MovieDetailsViewModel
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = arguments?.getInt(ID) ?: -1
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
            initVm()
            initObservers()
            requestData(550)
        }
        return binding.root
    }

    private fun FragmentMovieDetailsBinding.initVm() {
        viewModel = ViewModelProvider(this@MovieDetailsFragment)[MovieDetailsViewModel::class.java]
    }

    private fun FragmentMovieDetailsBinding.initViews(data: MovieListItem.MovieData) {

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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun FragmentMovieDetailsBinding.requestData(id: Int) {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/$id?api_key=${BuildConfig.API_KEY_V3}&language=en-US")
            val handler = Handler()
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    Log.d("SAY", "${urlConnection.responseCode}")
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val movieDetails: MovieDetailsDTO =
                        Gson().fromJson(getLines(bufferedReader), MovieDetailsDTO::class.java)
                    handler.post {
                        displayDetails(movieDetails)
                    }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("","Failed URI", e)
            e.printStackTrace()
        }
    }

    private fun displayDetails(movieDetails: MovieDetailsDTO) {
        with(binding) {
            tvName.text = movieDetails.title
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
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