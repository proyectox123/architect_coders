package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import kotlinx.coroutines.flow.Flow

class GetFavoriteMovieListWithChangesUseCase(private val movieRepository: MovieRepository){
    fun invoke(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovieListWithChanges()
}