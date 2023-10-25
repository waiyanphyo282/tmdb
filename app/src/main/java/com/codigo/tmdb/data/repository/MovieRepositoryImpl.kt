package com.codigo.tmdb.data.repository

import android.util.Log
import com.codigo.tmdb.data.Movie
import com.codigo.tmdb.data.datasource.local.MovieDao
import com.codigo.tmdb.data.datasource.remote.MovieRemoteDataSource
import com.codigo.tmdb.data.toExternal
import com.codigo.tmdb.data.toLocal
import com.codigo.tmdb.util.Constants.POPULAR
import com.codigo.tmdb.util.Constants.UPCOMING
import com.codigo.tmdb.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val remoteDataSource: MovieRemoteDataSource,
): MovieRepository {

    override suspend fun syncMovies() {
        Log.d(TAG, "syncMovies: started")
        remoteDataSource.getUpcomingMovies()?.let {
            Log.d(TAG, "syncMovies: Upcoming Movies - ${it.count()}")
            movieDao.upsertAll(it.toLocal(UPCOMING))
        }
        remoteDataSource.getPopularMovies()?.let {
            Log.d(TAG, "syncMovies: Popular Movies - ${it.count()}")
            movieDao.upsertAll(it.toLocal(POPULAR))
        }
    }

    override suspend fun getUpcomingMovies(): Flow<List<Movie>> {
        return movieDao.getUpcomingMovies().map {movies ->
            Log.d(TAG, "getUpcomingMovies: ${movies.size}")
            movies.toExternal()
        }
    }

    override suspend fun getPopularMovies(): Flow<List<Movie>> {
        return movieDao.getPopularMovies().map { movies ->
            Log.d(TAG, "getPopularMovies: ${movies.size}")
            movies.toExternal()
        }
    }

    override suspend fun toggleFavorite(movieId: Int) = withContext(Dispatchers.IO) {
        movieDao.toggleFavorite(movieId)
    }


}