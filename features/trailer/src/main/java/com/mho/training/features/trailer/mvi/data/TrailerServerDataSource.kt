package com.mho.training.features.trailer.mvi.data

import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.frameworkretrofit.data.requests.trailer.TrailerRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TrailerServerDataSource(
    private val errorUnableToFetchTrailers: String,
    private val errorDuringFetchingTrailers: String,
    private val trailerRequest: TrailerRequest,
    private val apiKey: String,
) : RemoteTrailerDataSource {

    override suspend fun getTrailerList(movieId: Int): DataResult<List<Trailer>> = withContext(
        Dispatchers.IO
    ) {
        safeApiCall(
            call = { requestTrailerList(movieId) },
            errorMessage = errorUnableToFetchTrailers
        )
    }

    private suspend fun requestTrailerList(movieId: Int): DataResult<List<Trailer>> {
        val response = trailerRequest.service
            .getTrailerListByMovieAsync(movieId, apiKey)

        if (response.isSuccessful) {
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainTrailer() })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingTrailers))
    }
}
