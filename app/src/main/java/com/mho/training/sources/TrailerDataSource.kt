package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.framework.data.remote.requests.trailer.TrailerRequest
import com.mho.training.BuildConfig
import com.mho.training.R
import com.mho.training.data.translators.toDomainTrailer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TrailerDataSource(private val resources: Resources) : RemoteTrailerDataSource {

    override suspend fun getTrailerList(movieId: Int): DataResult<List<Trailer>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestTrailerList(movieId) },
            errorMessage = resources.getString(R.string.error_unable_to_fetch_trailers)
        )
    }

    private suspend fun requestTrailerList(movieId: Int): DataResult<List<Trailer>> {
        val response = TrailerRequest.service
            .getTrailerListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainTrailer() })
            }
        }

        return DataResult.Error(IOException(resources.getString(R.string.error_during_fetching_trailers)))
    }
}