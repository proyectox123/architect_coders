package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie

class GetInTheatersMovieListUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(): List<Movie> =
        movieRepository.getInTheatersMovieList()
}