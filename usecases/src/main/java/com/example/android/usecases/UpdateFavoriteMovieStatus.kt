package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie

class UpdateFavoriteMovieStatus(private val movieRepository: MovieRepository) {

    suspend fun invoke(movie: Movie) =
        movieRepository.updateFavoriteMovieStatus(movie)

}