package com.example.android.data.sources

import com.example.android.domain.Movie

interface RemoteDataSource {
    suspend fun getTopRatedMovieList(region: String): List<Movie>
    suspend fun getPopularMovieList(region: String): List<Movie>
    suspend fun getInTheatersMovieList(region: String): List<Movie>
}