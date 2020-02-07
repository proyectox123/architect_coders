package com.mho.training.data.translators

import com.example.android.domain.MovieDetail
import com.example.android.frameworkretrofit.data.models.movie.MovieDetailResult

fun MovieDetailResult.toMovieDetailDomain(): MovieDetail =
    MovieDetail(
        id,
        title,
        generateDuration(duration),
        genres.map { it.toGenreDomain() }
    )

private fun generateDuration(duration: Int): String{
    val hours = duration / 60
    if(hours == 0){
        return "${duration}m"
    }

    val minutes = duration % 60
    if(minutes == 0){
        return "${hours}h"
    }

    return "${hours}h ${minutes}m"
}