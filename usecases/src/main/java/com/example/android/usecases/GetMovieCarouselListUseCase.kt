package com.example.android.usecases

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.MovieCarousel
import com.example.android.domain.result.DataResult

class GetMovieCarouselListUseCase(
    private val movieRepository: MovieRepository
){
    suspend fun invoke(): DataResult<List<MovieCarousel>> =
        movieRepository.getMovieCarouselList()
}