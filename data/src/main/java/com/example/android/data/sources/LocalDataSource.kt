package com.example.android.data.sources

import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getFavoriteMovieList(): DataResult<List<Movie>>
    fun getFavoriteMovieListWithChanges(): Flow<List<Movie>>
    suspend fun getFavoriteMovieStatus(movie: Movie): Boolean
    suspend fun updateFavoriteMovieStatus(movie: Movie): Boolean
}