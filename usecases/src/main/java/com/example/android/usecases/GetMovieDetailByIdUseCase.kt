package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult

class GetMovieDetailByIdUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(movieId: Int): DataResult<MovieDetail> =
        movieRepository.getMovieDetailById(movieId)
}