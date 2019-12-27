package com.mho.training.data.remote.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerMovie(val id: Int,
                       val title: String,
                       val releaseDate: String,
                       val posterPath: String,
                       val voteAverage: Double,
                       val plotSynopsis: String) : Parcelable