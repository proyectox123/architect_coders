package com.example.android.data.sources

import com.example.android.domain.Trailer
import com.example.android.framework.data.remote.requests.Result

interface RemoteTrailerDataSource {
    suspend fun getTrailerList(movieId: Int): Result<List<Trailer>>
}