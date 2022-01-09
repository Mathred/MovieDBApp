package com.example.moviedbapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.util.DebugLogger
import com.example.moviedbapp.model.repo.MovieDbApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDbApp : Application(), ImageLoaderFactory {
    lateinit var movieDbApi: MovieDbApi
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        configureOkHttp()
        configureRetrofit()
    }

    private fun configureOkHttp() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun configureRetrofit() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieDbApi = retrofit.create(MovieDbApi::class.java)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .componentRegistry {
                add(SvgDecoder(applicationContext))
            }
            .crossfade(true)
            .okHttpClient(okHttpClient)
            .logger(DebugLogger())
            .build()
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_SECURE_URL = "https://image.tmdb.org/t/p/"
        const val POSTER_SIZE_URL = "w500"
        const val URL_ORIGINAL_IMAGE_SIZE = "original"
    }
}