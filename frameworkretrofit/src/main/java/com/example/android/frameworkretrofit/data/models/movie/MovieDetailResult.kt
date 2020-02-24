package com.example.android.frameworkretrofit.data.models.movie

import com.example.android.frameworkretrofit.data.models.genre.ServerGenre
import com.google.gson.annotations.SerializedName

data class MovieDetailResult(
    @SerializedName("id") val id: Int,
    val title: String,
    @SerializedName("runtime") val duration: Int,
    @SerializedName("genres") val genres: List<ServerGenre>
)