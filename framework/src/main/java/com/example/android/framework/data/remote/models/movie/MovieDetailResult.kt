package com.example.android.framework.data.remote.models.movie

import com.example.android.framework.data.remote.models.genre.ServerGenre
import com.google.gson.annotations.SerializedName

data class MovieDetailResult(
    @SerializedName("id") val id: Int,
    val title: String,
    @SerializedName("runtime") val duration: Int,
    @SerializedName("genres") val genres: List<ServerGenre>
)