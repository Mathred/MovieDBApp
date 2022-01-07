package com.example.moviedbapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.moviedbapp.MovieDbApp
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.FragmentMovieDetailsBinding
import com.example.moviedbapp.extensions.getDateYear
import com.example.moviedbapp.utils.showErrorLoadingDialog
import com.example.moviedbapp.extensions.toImageUrl
import com.example.moviedbapp.extensions.toSvgUrl
import com.example.moviedbapp.utils.hideKeyboard
import com.example.moviedbapp.utils.openLocation
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
            initViews()
            requestData(id)
        }
        return binding.root
    }

    private fun initVm() {
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
    }

    private fun FragmentMovieDetailsBinding.initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) { appState ->
            when (appState) {
                is AppState.Loading -> {
                    progress.isVisible = true
                }
                is AppState.Error -> {
                    progress.isVisible = false
                    showErrorLoadingDialog(requireContext(), appState.error)
                }
                is AppState.Success -> {
                    progress.isVisible = false
                    ivMovie.load(appState.data.posterPath?.toImageUrl())
                    tvTitle.text = appState.data.title
                    tvOriginalTitle.text = appState.data.originalTitle
                    appState.data.releaseDate?.let {
                        tvYear.text = it.getDateYear()
                    }
                    appState.data.productionCompanies[0].logo_path?.let { logoPath ->
                        ivCompanyLogo.load(logoPath.toSvgUrl())
                    }
                    tvCompanyName.text = appState.data.productionCompanies[0].name

                    val countryName = appState.data.productionCountries[0].name
                    tvCountry.text = countryName
                    vShowMap.setOnClickListener {
                        openLocation(countryName)
                    }

                    appState.data.note?.let { note ->
                        etNote.setText(note)
                    }
                    btnSaveNote.setOnClickListener {
                        viewModel.updateNote(appState.data.id, etNote.text.toString())
                        this@MovieDetailsFragment.context?.hideKeyboard(root)
                        etNote.clearFocus()
                    }
                    btnReset.setOnClickListener {
                        etNote.setText(appState.data.note ?: "")
                        this@MovieDetailsFragment.context?.hideKeyboard(root)
                        etNote.clearFocus()
                    }
                    btnDeleteNote.setOnClickListener {
                        viewModel.deleteNote(appState.data.id)
                        etNote.setText("")
                        this@MovieDetailsFragment.context?.hideKeyboard(root)
                        etNote.clearFocus()
                    }
                }
            }
        }
        viewModel.needUpdateNote.observe(viewLifecycleOwner) {
            if (it) {
                btnSaveNote.setBackgroundColor(Color.BLUE)
                btnSaveNote.isClickable = true
                btnReset.setBackgroundColor(Color.BLUE)
                btnReset.isClickable = true
            } else {
                btnSaveNote.setBackgroundColor(Color.GRAY)
                btnSaveNote.isClickable = false
                btnReset.setBackgroundColor(Color.GRAY)
                btnReset.isClickable = false
            }
        }
        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), resources.getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun FragmentMovieDetailsBinding.initViews() {
        etNote.doOnTextChanged { text, _, _, _ ->
            viewModel.checkNoteChanged(text.toString())
            if (text.isNullOrBlank()) {
                btnDeleteNote.setBackgroundColor(Color.GRAY)
                btnDeleteNote.isClickable = false
            } else {
                btnDeleteNote.setBackgroundColor(Color.RED)
                btnDeleteNote.isClickable = true
            }
        }
        container.setOnClickListener {
            requireContext().hideKeyboard(this@MovieDetailsFragment.requireView())
        }
    }

    private fun requestData(id: Int) {
        viewModel.getData((activity?.application as? MovieDbApp)?.movieDbApi, id)
    }

    companion object {

        const val MOVIE_ID = "MOVIE_ID"
        fun newInstance(movieId: Int) = MovieDetailsFragment().apply {
            arguments = bundleOf(MOVIE_ID to movieId)
        }
    }

}