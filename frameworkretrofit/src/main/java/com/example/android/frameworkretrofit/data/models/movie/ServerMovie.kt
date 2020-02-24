package com.example.android.frameworkretrofit.data.models.movie

import com.google.gson.annotations.SerializedName

data class ServerMovie(
    @SerializedName("id") val id: Int,
    val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val plotSynopsis: String
)