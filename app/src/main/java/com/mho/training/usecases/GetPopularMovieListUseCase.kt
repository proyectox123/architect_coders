package com.mho.training.usecases

import com.mho.training.data.MovieRepository
import com.mho.training.domain.Movie

class GetPopularMovieListUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(): List<Movie> =
        movieRepository.getPopularMovieList()
}