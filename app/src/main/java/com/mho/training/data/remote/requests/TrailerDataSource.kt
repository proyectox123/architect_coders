package com.mho.training.data.remote.requests

import com.mho.training.data.remote.requests.trailers.TrailerRequest
import com.mho.training.data.source.RemoteTrailerDataSource
import com.mho.training.data.translators.toDomainTrailer
import com.mho.training.domain.Trailer

class TrailerDataSource : RemoteTrailerDataSource {

    override suspend fun getTrailerList(movieId: Int): List<Trailer> =
        TrailerRequest(movieId)
            .requestTrailerList()
            .map { it.toDomainTrailer() }
}