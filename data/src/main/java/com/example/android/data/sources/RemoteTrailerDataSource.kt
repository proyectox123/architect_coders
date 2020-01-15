package com.example.android.data.sources

import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult

interface RemoteTrailerDataSource {
    suspend fun getTrailerList(movieId: Int): DataResult<List<Trailer>>
}