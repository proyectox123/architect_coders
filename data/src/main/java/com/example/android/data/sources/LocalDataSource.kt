package com.example.android.data.sources

import com.example.android.domain.Movie
import com.example.android.framework.data.remote.requests.Result
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getFavoriteMovieList(): Result<List<Movie>>
    fun getFavoriteMovieListWithChanges(): Flow<List<Movie>>
    suspend fun getFavoriteMovieStatus(movie: Movie): Boolean
    suspend fun updateFavoriteMovieStatus(movie: Movie): Boolean
}