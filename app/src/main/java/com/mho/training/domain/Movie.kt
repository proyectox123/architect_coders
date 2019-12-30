package com.mho.training.domain

import android.content.Context
import android.os.Parcelable
import com.mho.training.R
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: Double,
    val plotSynopsis: String,
    val favorite: Boolean
): Parcelable {

    fun getVoteAverageLabel(context: Context): String {
        val voteAverageLabel = "$voteAverage/10"
        return context.getString(R.string.text_movie_detail_vote_average, voteAverageLabel)
    }
}