package com.mho.training.sources

import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.domain.Trailer
import com.mho.training.BuildConfig
import com.mho.training.data.remote.requests.RetrofitRequest
import com.mho.training.data.translators.toDomainTrailer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrailerDataSource : RemoteTrailerDataSource {

    override suspend fun getTrailerList(movieId: Int): List<Trailer> = withContext(Dispatchers.IO) {
        RetrofitRequest.service
            .getTrailerListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)
            .await()
            .results
            .map { it.toDomainTrailer() }
    }
}