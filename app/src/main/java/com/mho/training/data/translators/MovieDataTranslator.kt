package com.mho.training.data.translators

import android.content.res.Resources
import com.example.android.domain.Movie
import com.example.android.framework.data.local.database.tables.MovieEntity
import com.example.android.framework.data.remote.models.movie.ServerMovie
import com.mho.training.R
import com.mho.training.models.ParcelableMovie
import com.mho.training.utils.Constants

fun MovieEntity.toDomainMovie(resources: Resources): Movie =
    Movie(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        getVoteAverageLabel(resources, voteAverage),
        plotSynopsis
    )

fun ServerMovie.toDomainMovie(resources: Resources): Movie =
    Movie(
        id,
        title,
        generateReleaseDate(releaseDate),
        generateMoviePosterAbsolutePath(posterPath),
        voteAverage,
        getVoteAverageLabel(resources, voteAverage),
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

private fun generateReleaseDate(releaseDate: String?): String{
    return if(releaseDate.isNullOrBlank()) "-" else releaseDate
}

private fun getVoteAverageLabel(resources: Resources, voteAverage: Double): String {
    val voteAverageLabel = "$voteAverage/10"
    return resources.getString(R.string.text_movie_detail_vote_average, voteAverageLabel)
}

private fun generateMoviePosterAbsolutePath(posterPath: String)
        = Constants.URL_IMAGE_TBMD + posterPath