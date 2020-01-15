package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult

class GetMovieListByPersonUseCase(private val movieRepository: MovieRepository){
    suspend fun invoke(personId: Int): DataResult<List<Movie>> =
        movieRepository.getMovieListByPerson(personId)
}