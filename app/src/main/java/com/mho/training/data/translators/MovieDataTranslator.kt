package com.mho.training.data.translators

import android.content.res.Resources
import com.example.android.domain.Movie
import com.example.android.framework.data.remote.models.ServerMovie
import com.mho.training.R
import com.example.android.framework.data.local.database.tables.MovieEntity
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
        releaseDate,
        generateMoviePosterAbsolutePath(posterPath),
        voteAverage,
        getVoteAverageLabel(resources, voteAverage),
        plotSynopsis
    )

fun Movie.toEntityMovie(): MovieEntity =
    MovieEntity(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        plotSynopsis
    )

private fun getVoteAverageLabel(resources: Resources, voteAverage: Double): String {
    val voteAverageLabel = "$voteAverage/10"
    return resources.getString(R.string.text_movie_detail_vote_average, voteAverageLabel)
}

private fun generateMoviePosterAbsolutePath(posterPath: String)
        = Constants.URL_IMAGE_TBMD + posterPath