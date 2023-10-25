package com.codigo.tmdb.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codigo.tmdb.data.Movie
import com.codigo.tmdb.data.datasource.remote.NetworkMovie
import com.codigo.tmdb.data.repository.MovieRepository
import com.codigo.tmdb.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> get() = _upcomingMovies

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    init {
        viewModelScope.launch {
            repo.syncMovies()
        }
        getMovies()
    }


    private fun getMovies() = viewModelScope.launch {
        launch {
            repo.getUpcomingMovies().collectLatest {
                Log.d(TAG, "getUpcomingMovies: ${it.size}")
                _upcomingMovies.postValue(it)
            }
        }
        launch {
            repo.getPopularMovies().collectLatest {
                Log.d(TAG, "getPopularMovies: ${it.size}")
                _popularMovies.postValue(it)
            }
        }
    }

    fun toggleFavorite(movieId: Int)= viewModelScope.launch {
        repo.toggleFavorite(movieId)
    }

}