package com.mho.training.data.source

import com.mho.training.domain.Movie

interface RemoteDataSource {
    suspend fun getTopRatedMovieList(region: String): List<Movie>
    suspend fun getPopularMovieList(region: String): List<Movie>
}