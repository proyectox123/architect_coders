package com.mho.training.data.translators

import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.ServerMovie
import com.mho.training.domain.Movie

fun MovieEntity.toDomainMovie(): Movie = Movie(
    id,
    title,
    releaseDate.toString(),
    posterPath,
    voteAverage,
    plotSynopsis,
    false
)

fun ServerMovie.toDomainMovie(): Movie = Movie(
    id,
    title,
    releaseDate,
    posterPath,
    voteAverage,
    plotSynopsis,
    false
)