package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie

class GetPopularMovieListUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(): List<Movie> =
        movieRepository.getPopularMovieList()
}