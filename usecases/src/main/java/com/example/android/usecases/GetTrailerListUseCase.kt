package com.example.android.usecases

import com.example.android.data.repositories.TrailerRepository
import com.example.android.domain.Trailer
import com.example.android.framework.data.remote.requests.Result

class GetTrailerListUseCase(private val trailerRepository: TrailerRepository){

    suspend fun invoke(movieId: Int): Result<List<Trailer>> =
        trailerRepository.getTrailerList(movieId)
}