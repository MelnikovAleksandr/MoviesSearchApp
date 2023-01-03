package ru.asmelnikov.android.moviesearchapp.ui.details

import ru.asmelnikov.android.moviesearchapp.data.moviedetails.MovieDetails
import ru.asmelnikov.android.moviesearchapp.remote.MovieInterface
import ru.asmelnikov.android.moviesearchapp.utils.Constants.API_KEY
import ru.asmelnikov.android.moviesearchapp.utils.Status
import ru.asmelnikov.android.moviesearchapp.utils.Result

class MovieDetailsRepository(private val movieInterface: MovieInterface) {

    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails> {

        return try {
            val response = movieInterface.getMovieDetails(imdbId, API_KEY)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body(), null)
            } else {
                Result(Status.ERROR, null, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null, null)
        }
    }
}