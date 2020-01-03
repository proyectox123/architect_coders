package com.example.android.data.sources

import com.example.android.domain.Movie

interface LocalDataSource {
    suspend fun getFavoriteMovieList(): List<Movie>
}