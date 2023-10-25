package com.codigo.tmdb.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.codigo.tmdb.util.Constants.POPULAR
import com.codigo.tmdb.util.Constants.UPCOMING
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsert(movie: LocalMovie)

    @Upsert
    suspend fun upsertAll(movies: List<LocalMovie>)

    @Query("select * from movie where type=:type")
    fun getUpcomingMovies(type: String = UPCOMING): Flow<List<LocalMovie>>

    @Query("select * from movie where type=:type")
    fun getPopularMovies(type: String = POPULAR): Flow<List<LocalMovie>>

    @Query("update movie set isFavorite= NOT isFavorite where id=:movieId")
    fun toggleFavorite(movieId: Int)
}