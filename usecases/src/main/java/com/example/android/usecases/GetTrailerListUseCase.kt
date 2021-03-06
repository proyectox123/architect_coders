package com.example.android.usecases

import com.example.android.data.repositories.TrailerRepository
import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult

class GetTrailerListUseCase(private val trailerRepository: TrailerRepository){

    suspend fun invoke(movieId: Int): DataResult<List<Trailer>> =
        trailerRepository.getTrailerList(movieId)
}