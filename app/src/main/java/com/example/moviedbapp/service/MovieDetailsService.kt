package com.example.moviedbapp.service

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviedbapp.BuildConfig
import com.example.moviedbapp.model.entities.MovieDetailsDTO
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_EMPTY_INPUT_DATA
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_EMPTY_INTENT
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_ERROR
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_ERROR_MESSAGE
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_INTENT_FILTER
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_LOAD_RESULT
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_MALFORMED_URL
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_RESPONSE_SUCCESS
import com.example.moviedbapp.ui.MovieDetailsFragment.Companion.MOVIE_DETAILS_EXTRA
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val MOVIE_ID = "MOVIE_ID"

class MovieDetailsService(
    name: String = "MovieDetailsService"
) : IntentService(name) {

    private val broadcastIntent = Intent(MOVIE_DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(MOVIE_ID, -1)
            if (id == -1) {
                onEmptyInputData()
            } else {
                loadMovieDetails(id)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovieDetails(id: Int) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/$id?api_key=${BuildConfig.API_KEY_V3}&language=en-US")
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = REQUEST_METHOD
                    urlConnection.readTimeout = REQUEST_TIMEOUT
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val movieDetails: MovieDetailsDTO =
                        Gson().fromJson(getLines(bufferedReader), MovieDetailsDTO::class.java)
                    onResponse(movieDetails)
                } catch (e: Exception) {
                    onErrorRequest(e.message ?: "ERROR")
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieDetails: MovieDetailsDTO) {
        putLoadResult(MOVIE_DETAILS_RESPONSE_SUCCESS)
        broadcastIntent.putExtra(MOVIE_DETAILS_EXTRA, movieDetails)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(MOVIE_DETAILS_EMPTY_INTENT)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyInputData() {
        putLoadResult(MOVIE_DETAILS_EMPTY_INPUT_DATA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(MOVIE_DETAILS_ERROR)
        broadcastIntent.putExtra(MOVIE_DETAILS_ERROR_MESSAGE, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(MOVIE_DETAILS_MALFORMED_URL)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(MOVIE_DETAILS_LOAD_RESULT, result)
    }

    companion object {
        const val REQUEST_METHOD = "GET"
        const val REQUEST_TIMEOUT = 10000
    }

}