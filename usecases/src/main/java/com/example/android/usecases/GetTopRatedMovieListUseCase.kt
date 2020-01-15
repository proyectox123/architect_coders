package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult

class GetTopRatedMovieListUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(): DataResult<List<Movie>> =
        movieRepository.getTopRatedMovieList()
}