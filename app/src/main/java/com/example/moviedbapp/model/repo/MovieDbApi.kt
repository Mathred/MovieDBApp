package com.example.moviedbapp.model.repo

import com.example.moviedbapp.BuildConfig
import com.example.moviedbapp.extensions.Paged
import com.example.moviedbapp.model.entities.ActorData
import com.example.moviedbapp.model.entities.MovieBriefDTO
import com.example.moviedbapp.model.entities.MovieDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {

    @GET("https://api.themoviedb.org/3/movie/top_rated")
    suspend fun getTopRatedList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY_V3,
        @Query("page") page: Int = 1
    ): Paged<MovieBriefDTO>

    @GET("https://api.themoviedb.org/3/movie/popular")
    suspend fun getPopularMovieList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY_V3,
        @Query("page") page: Int = 1
    ): Paged<MovieBriefDTO>

    @GET("https://api.themoviedb.org/3/movie/upcoming")
    suspend fun getUpcomingMovieList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY_V3,
        @Query("page") page: Int = 1
    ): Paged<MovieBriefDTO>

    @GET("https://api.themoviedb.org/3/trending/{media_type}/{time_window}")
    suspend fun getTrendingList(
        @Path("media_type") mediaType: String = "movie",
        @Path("time_window") timeWindow: String = "week",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY_V3
    ): Paged<MovieBriefDTO>

    @GET("https://api.themoviedb.org/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY_V3
    ): MovieDetailsDTO

    companion object {
        enum class MediaType(val apiPath: String) {
            ALL("all"),
            MOVIE("movie"),
            TV("tv"),
            PERSON("person")
        }
        enum class TimeWindow(val apiPath: String) {
            DAY("day"),
            WEEK("week")
        }
    }
}