package com.codigo.tmdb.data

import com.codigo.tmdb.data.datasource.local.LocalMovie
import com.codigo.tmdb.data.datasource.remote.NetworkMovie


fun LocalMovie.toExternal() = Movie(
    id, adult, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount, isFavorite
)

@JvmName("localToExternal")
fun List<LocalMovie>.toExternal() = map(LocalMovie::toExternal)


fun NetworkMovie.toLocal(type: String) = LocalMovie(
    id, adult, backdropPath, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount, false, type
)

@JvmName("networkToLocal")
fun List<NetworkMovie>.toLocal(type: String) = map { it.toLocal(type) }