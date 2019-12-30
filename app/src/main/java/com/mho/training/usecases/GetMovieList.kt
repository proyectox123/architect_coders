package com.mho.training.usecases

import com.mho.training.data.MovieRepository
import com.mho.training.domain.Movie

class GetMovieList (private val movieRepository: MovieRepository){
    suspend fun getTopRatedMovieList(): List<Movie> =
        movieRepository.getTopRatedMovieList()

    suspend fun getPopularMovieList(): List<Movie> =
        movieRepository.getPopularMovieList()

    suspend fun getFavoriteMovieList(): List<Movie> =
        movieRepository.getFavoriteMovieList()
}