package com.example.android.data.sources

import androidx.lifecycle.LiveData
import com.example.android.domain.Movie

interface LocalDataSource {
    suspend fun getFavoriteMovieList(): List<Movie>
    fun getFavoriteMovieListWithChanges(): LiveData<List<Movie>>
    suspend fun getFavoriteMovieStatus(movie: Movie): Boolean
    suspend fun updateFavoriteMovieStatus(movie: Movie): Boolean
}