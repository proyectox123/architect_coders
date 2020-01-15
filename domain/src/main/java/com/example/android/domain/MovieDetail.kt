package com.example.android.domain

data class MovieDetail(
    val id: Int,
    val title: String,
    val duration: String,
    val genreList: List<Genre>?
)