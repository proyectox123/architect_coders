package com.example.android.frameworkretrofit.data.models.movie

import com.google.gson.annotations.SerializedName

data class MovieCreditsResult(
    @SerializedName("cast") val results: List<ServerMovie>
)