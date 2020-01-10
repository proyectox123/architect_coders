package com.example.android.framework.data.remote.models.movie

import com.google.gson.annotations.SerializedName

data class MovieCreditsResult(
    @SerializedName("cast") val results: List<ServerMovie>
)