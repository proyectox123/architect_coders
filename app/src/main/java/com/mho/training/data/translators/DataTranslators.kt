package com.mho.training.data.translators

import android.content.res.Resources
import com.example.android.domain.Movie
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.mho.training.R
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.models.ServerMovie
import com.mho.training.data.remote.models.ServerReview
import com.mho.training.data.remote.models.ServerTrailer
import com.mho.training.parcelables.MovieParcelable

fun MovieEntity.toDomainMovie(resources: Resources): Movie =
    Movie(
        id,
        title,
        releaseDate.toString(),
        posterPath,
        getVoteAverageLabel(resources, voteAverage),
        plotSynopsis,
        false
    )

fun ServerMovie.toDomainMovie(resources: Resources): Movie =
    Movie(
        id,
        title,
        releaseDate,
        posterPath,
        getVoteAverageLabel(resources, voteAverage),
        plotSynopsis,
        false
    )

fun ServerTrailer.toDomainTrailer(): Trailer =
    Trailer(
        id,
        name,
        thumbnail,
        videoPath
    )

fun ServerReview.toDomainReview(): Review =
    Review(
        id,
        author,
        content,
        url
    )

fun Movie.toParcelableMovie(): MovieParcelable =
    MovieParcelable(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        plotSynopsis,
        favorite
    )

fun MovieParcelable?.toDomainMovie(): Movie? = if(this == null){
    null
} else {
    Movie(
        id,
        title,
        releaseDate,
        posterPath,
        voteAverage,
        plotSynopsis,
        favorite
    )
}

private fun getVoteAverageLabel(resources: Resources, voteAverage: Double): String {
    val voteAverageLabel = "$voteAverage/10"
    return resources.getString(R.string.text_movie_detail_vote_average, voteAverageLabel)
}