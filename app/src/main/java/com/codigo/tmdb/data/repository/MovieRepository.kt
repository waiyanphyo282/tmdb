package com.codigo.tmdb.data.repository

import com.codigo.tmdb.data.Movie
import com.codigo.tmdb.data.datasource.remote.NetworkMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun syncMovies()

    suspend fun getUpcomingMovies(): Flow<List<Movie>>

    suspend fun getPopularMovies(): Flow<List<Movie>>

    suspend fun toggleFavorite(movieId: Int)
}