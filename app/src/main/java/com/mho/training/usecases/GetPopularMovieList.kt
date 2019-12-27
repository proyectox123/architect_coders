package com.mho.training.usecases

import com.mho.training.data.MovieRepository
import com.mho.training.domain.Movie

class GetPopularMovieList (private val movieRepository: MovieRepository){
    suspend fun invoke(): List<Movie> = movieRepository.getPopularMovieList()
}