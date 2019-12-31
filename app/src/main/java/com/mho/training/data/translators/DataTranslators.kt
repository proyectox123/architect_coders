package com.mho.training.data.translators

import android.content.res.Resources
import com.mho.training.R
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.ServerMovie
import com.mho.training.data.remote.models.ServerTrailer
import com.mho.training.domain.Movie
import com.mho.training.domain.Trailer

fun MovieEntity.toDomainMovie(resources: Resources): Movie = Movie(
    id,
    title,
    releaseDate.toString(),
    posterPath,
    getVoteAverageLabel(resources, voteAverage),
    plotSynopsis,
    false
)

fun ServerMovie.toDomainMovie(resources: Resources): Movie = Movie(
    id,
    title,
    releaseDate,
    posterPath,
    getVoteAverageLabel(resources, voteAverage),
    plotSynopsis,
    false
)

fun ServerTrailer.toDomainTrailer(): Trailer = Trailer(
    id,
    name,
    thumbnail,
    videoPath
)

private fun getVoteAverageLabel(resources: Resources, voteAverage: Double): String {
    val voteAverageLabel = "$voteAverage/10"
    return resources.getString(R.string.text_movie_detail_vote_average, voteAverageLabel)
}