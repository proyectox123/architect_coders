package com.mho.training.parcelables

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieParcelable(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: String,
    val plotSynopsis: String,
    val favorite: Boolean
): Parcelable