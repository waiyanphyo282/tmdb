package com.codigo.tmdb.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codigo.tmdb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

    fun toggleFavorite(movieId: Int) = viewModelScope.launch { repo.toggleFavorite(movieId) }
}