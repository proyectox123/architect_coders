package com.example.android.data.sources

import com.example.android.domain.Movie

interface LocalDataSource {
    suspend fun getFavoriteMovieList(): List<Movie>
    suspend fun getFavoriteMovieStatus(movie: Movie): Boolean
    suspend fun updateFavoriteMovieStatus(movie: Movie): Boolean
}