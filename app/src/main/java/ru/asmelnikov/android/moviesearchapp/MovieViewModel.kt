package ru.asmelnikov.android.moviesearchapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.asmelnikov.android.moviesearchapp.data.moviedetails.MovieDetails
import ru.asmelnikov.android.moviesearchapp.remote.MovieInterface
import ru.asmelnikov.android.moviesearchapp.ui.details.MovieDetailsRepository
import ru.asmelnikov.android.moviesearchapp.ui.movie.MoviePaging
import ru.asmelnikov.android.moviesearchapp.utils.Events
import ru.asmelnikov.android.moviesearchapp.utils.Result
import ru.asmelnikov.android.moviesearchapp.utils.Status
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor
    (
    private val movieInterface: MovieInterface,
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val query = MutableLiveData("")
    private val _movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()
    val movieDetails: LiveData<Events<Result<MovieDetails>>> = _movieDetails


    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(query, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    fun getMovieDetails(imdbId: String) = viewModelScope.launch {
        _movieDetails.postValue(Events(Result(Status.LOADING, null, null)))
        _movieDetails.postValue(Events(repository.getMovieDetails(imdbId)))
    }
}