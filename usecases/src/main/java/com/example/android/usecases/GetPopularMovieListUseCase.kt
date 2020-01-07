package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.framework.data.remote.requests.Result

class GetPopularMovieListUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(): Result<List<Movie>> =
        movieRepository.getPopularMovieList()
}