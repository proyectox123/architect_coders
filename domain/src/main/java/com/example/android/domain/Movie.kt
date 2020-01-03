package com.example.android.domain

data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: String,
    val plotSynopsis: String,
    val favorite: Boolean
)