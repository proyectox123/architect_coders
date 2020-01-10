package com.example.android.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val posterPath: String,
    val voteAverage: Double,
    val voteAverageLabel: String,
    val plotSynopsis: String
): Parcelable