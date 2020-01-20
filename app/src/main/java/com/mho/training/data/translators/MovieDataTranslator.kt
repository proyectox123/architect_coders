package com.mho.training.data.translators

import com.example.android.domain.Movie
import com.example.android.framework.data.local.database.tables.MovieEntity
import com.example.android.framework.data.remote.models.movie.ServerMovie
import com.mho.training.models.ParcelableMovie
import com.mho.training.utils.Constants

fun MovieEntity.toDomainMovie(formatVoteAverage: String): Movie =
    Movie(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        getVoteAverageLabel(voteAverage, formatVoteAverage),
        plotSynopsis
    )

fun ServerMovie.toDomainMovie(formatVoteAverage: String): Movie =
    Movie(
        id,
        title,
        generateReleaseDate(releaseDate),
        generateMoviePosterAbsolutePath(posterPath),
        voteAverage,
        getVoteAverageLabel(voteAverage, formatVoteAverage),
        plotSynopsis
    )

fun Movie.toEntityMovie(): MovieEntity =
    MovieEntity(
        id,
        title,
        generateReleaseDate(releaseDate),
        posterPath,
        voteAverage,
        plotSynopsis
    )

fun Movie.toParcelableMovie(): ParcelableMovie =
    ParcelableMovie(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        voteAverageLabel,
        plotSynopsis
    )

fun ParcelableMovie.toDomainMovie(): Movie =
    Movie(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        voteAverageLabel,
        plotSynopsis
    )

private fun generateReleaseDate(releaseDate: String?) =
    if(releaseDate.isNullOrBlank()) "-" else releaseDate

private fun getVoteAverageLabel(voteAverage: Double, formatVoteAverage: String) =
    formatVoteAverage.format("$voteAverage/10")

private fun generateMoviePosterAbsolutePath(posterPath: String) =
    Constants.URL_IMAGE_TBMD + posterPath