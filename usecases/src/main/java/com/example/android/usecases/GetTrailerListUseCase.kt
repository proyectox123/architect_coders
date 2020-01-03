package com.example.android.usecases

import com.example.android.data.repositories.TrailerRepository
import com.example.android.domain.Trailer

class GetTrailerListUseCase(private val trailerRepository: TrailerRepository){

    suspend fun invoke(movieId: Int): List<Trailer> =
        trailerRepository.getTrailerList(movieId)
}