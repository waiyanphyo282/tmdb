package com.codigo.tmdb.data.apiservice

import com.codigo.tmdb.data.datasource.remote.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>
}