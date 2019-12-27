package com.mho.training.data.source

import com.mho.training.domain.Movie

interface RemoteDataSource {
    suspend fun getTopRatedMovieList(): List<Movie>
    suspend fun getPopularMovieList(): List<Movie>
}