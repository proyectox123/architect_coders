package com.example.android.data.sources

import com.example.android.domain.Movie
import com.example.android.framework.data.remote.requests.Result

interface RemoteDataSource {
    suspend fun getTopRatedMovieList(region: String): Result<List<Movie>>
    suspend fun getPopularMovieList(region: String): Result<List<Movie>>
    suspend fun getInTheatersMovieList(region: String): Result<List<Movie>>
}