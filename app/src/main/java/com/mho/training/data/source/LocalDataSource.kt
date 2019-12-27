package com.mho.training.data.source

import com.mho.training.domain.Movie

interface LocalDataSource {
    suspend fun getFavoriteMovieList(): List<Movie>
}