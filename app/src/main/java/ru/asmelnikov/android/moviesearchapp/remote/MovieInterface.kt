package ru.asmelnikov.android.moviesearchapp.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.asmelnikov.android.moviesearchapp.data.MovieResponse
import ru.asmelnikov.android.moviesearchapp.data.moviedetails.MovieDetails

interface MovieInterface {

    @GET("/")
    suspend fun getAllMovies(
        @Query("s") s: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apiKey") apiKey: String
    ): Response<MovieDetails>
}