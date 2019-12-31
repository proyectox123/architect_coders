package com.mho.training.usecases

import com.mho.training.data.TrailerRepository
import com.mho.training.domain.Trailer

class GetTrailerListUseCase(private val trailerRepository: TrailerRepository){

    suspend fun invoke(movieId: Int): List<Trailer> =
        trailerRepository.getTrailerList(movieId)
}