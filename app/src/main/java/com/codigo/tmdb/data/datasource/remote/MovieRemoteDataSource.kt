package com.codigo.tmdb.data.datasource.remote

import com.codigo.tmdb.data.apiservice.MovieApiService
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val api: MovieApiService) {
    suspend fun getUpcomingMovies(): List<NetworkMovie>? {
        val response = api.getUpcomingMovies()
        return if (response.isSuccessful) {
            response.body()?.results
        } else null
    }

    suspend fun getPopularMovies(): List<NetworkMovie>? {
        val response = api.getPopularMovies()
        return if (response.isSuccessful) {
            response.body()?.results
        } else null
    }

}