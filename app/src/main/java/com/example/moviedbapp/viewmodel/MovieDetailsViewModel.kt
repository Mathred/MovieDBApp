package com.example.moviedbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.MovieDbApp.Companion.getMovieDetailsDao
import com.example.moviedbapp.R
import com.example.moviedbapp.extensions.toDate
import com.example.moviedbapp.model.entities.MovieDetailsViewData
import com.example.moviedbapp.model.repo.LocalRepo
import com.example.moviedbapp.model.repo.LocalRepoImpl
import com.example.moviedbapp.model.repo.MovieDbApi
import com.example.moviedbapp.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val localRepo: LocalRepo = LocalRepoImpl(getMovieDetailsDao())
) : ViewModel() {
    val liveData: LiveData<AppState<MovieDetailsViewData>> get() = _liveData
    private var _liveData = MutableLiveData<AppState<MovieDetailsViewData>>()

    val needUpdateNote: LiveData<Boolean> get() = _needUpdateNote
    private var _needUpdateNote = MutableLiveData(false)

    val showToast: LiveData<Int> get() = _showToast
    private var _showToast = SingleLiveEvent<Int>()

    private var initNote: String? = null

    fun getData(movieDbApi: MovieDbApi?, id: Int) {
        movieDbApi?.let {
            viewModelScope.launch(Dispatchers.IO) {
                _liveData.postValue(AppState.Loading)
                runCatching {
                    val movieDetails = movieDbApi.getMovieDetails(id)
                    localRepo.saveMovieHistoryEntity(movieDetails)
                    localRepo.saveMovieNoteEntity(movieDetails)
                    initNote = localRepo.getNoteByMovieId(id)
                    _liveData.postValue(
                        AppState.Success(
                            MovieDetailsViewData(
                                id = movieDetails.id,
                                title = movieDetails.title,
                                originalTitle = movieDetails.original_title,
                                posterPath = movieDetails.poster_path,
                                productionCompanies = movieDetails.production_companies,
                                productionCountries = movieDetails.production_countries,
                                releaseDate = movieDetails.release_date.toDate(),
                                note = initNote
                            )
                        )
                    )
                }.onFailure {
                    _liveData.postValue(AppState.Error(it))
                }
            }
        }
    }

    fun checkNoteChanged(newNote: String) {
        if (newNote != initNote) {
            _needUpdateNote.postValue(true)
        } else {
            _needUpdateNote.postValue(false)
        }
    }

    fun updateNote(id: Int, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepo.updateNoteByMovieId(id, note)
            initNote = note
            _needUpdateNote.postValue(false)
            _showToast.postValue(R.string.toast_note_saved)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepo.deleteNoteByMovieId(id)
            initNote = null
            _needUpdateNote.postValue(false)
            _showToast.postValue(R.string.toast_note_deleted)
        }
    }

}